import { useMemo } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import PhoneInput, { Country } from 'react-phone-number-input';
import 'react-phone-number-input/style.css';
import FormInput from '../../../../../components/common/form-input/FormInput';
import SelectInput, {
  SelectOption,
} from '../../../../../components/common/select-input/SelectInput';
import { IDefaultObject } from '../../../../../types/interfaces/common/IDefaultObject';
import { ICity } from '../../../../../types/interfaces/location/ICity';
import { ICountry } from '../../../../../types/interfaces/location/ICountry';
import {
  AdditionalStepperForm,
  IAdditionalStepper,
  StepperButtonEnum,
} from '../types';
import {
  ADDRESS_VALIDATIONS,
  PHONE_NUMBER_VALIDATIONS,
  SELECT_TOWN_VALIDATIONS,
} from './validations';

interface RegisterFormAdditionalProps {
  currentState: IAdditionalStepper;
  cities: ICity[];
  countries: ICountry[];
  previousStep: (v: IAdditionalStepper) => void;
  submit: (v: IAdditionalStepper) => void;
}

interface ICountriesReduced {
  list: Country[];
  mapAlpha2ToId: IDefaultObject<string>;
  mapIdToAlpha2: IDefaultObject<Country>;
}

const DEFAULT_COUNTRY: Country = 'BG';

function RegisterFormAdditional({
  currentState,
  ...props
}: RegisterFormAdditionalProps) {
  const {
    handleSubmit,
    control,
    setValue,
    formState: { errors },
  } = useForm<AdditionalStepperForm>({
    defaultValues: {
      line: currentState.primaryAddress.line,
      cityId: currentState.primaryAddress.cityId,
      country: currentState.phone.country,
      number: currentState.phone.number,
    },
    mode: 'onChange',
  });

  const cities: SelectOption[] = useMemo(
    () =>
      props.cities.map((x) => ({
        value: x.id,
        label: `${x.name}, ${x.country.name}`,
      })),
    [props.cities]
  );

  const countries = useMemo(
    () =>
      props.countries.reduce<ICountriesReduced>(
        (acc, x) => {
          return {
            list: [...acc.list, x.alpha2 as Country],
            mapAlpha2ToId: {
              ...acc.mapAlpha2ToId,
              [x.alpha2]: x.id,
            },
            mapIdToAlpha2: {
              ...acc.mapIdToAlpha2,
              [x.id]: x.alpha2 as Country,
            },
          };
        },
        { list: [], mapAlpha2ToId: {}, mapIdToAlpha2: {} }
      ),
    [props.countries]
  );

  const handleOptionChange = (
    key: keyof AdditionalStepperForm,
    vFromInput: string | undefined
  ): void => {
    const v = vFromInput?.toString() || '';

    setValue(key, v, {
      shouldValidate: true,
      shouldDirty: true,
      shouldTouch: true,
    });
  };

  // Handle form submission
  const onSubmit: SubmitHandler<AdditionalStepperForm> = (data, e) => {
    if (!(e?.nativeEvent instanceof SubmitEvent)) return;
    const submitter = e?.nativeEvent?.submitter;
    if (!(submitter instanceof HTMLButtonElement)) return;

    const typeFromButton =
      e.nativeEvent.submitter?.getAttribute('data-button-type') || '';

    const type: StepperButtonEnum =
      typeFromButton in StepperButtonEnum
        ? StepperButtonEnum[typeFromButton as keyof typeof StepperButtonEnum]
        : StepperButtonEnum.SUBMIT;

    const dataFinish: IAdditionalStepper = {
      primaryAddress: {
        line: data.line.trim(),
        cityId: data.cityId.trim(),
      },
      phone: {
        country:
          countries.mapAlpha2ToId[data.country] ||
          countries.mapAlpha2ToId[DEFAULT_COUNTRY],
        number: data.number,
      },
    };

    if (type === StepperButtonEnum.SUBMIT) {
      props.submit(dataFinish);
    } else if (type === StepperButtonEnum.PREVIOUS) {
      props.previousStep(dataFinish);
    }
  };

  return (
    <>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <div className="form-row">
          <div className="form-item mb-4">
            <FormInput
              control={control}
              name="cityId"
              customComponent={
                <SelectInput
                  defaultValue={cities.find(
                    (x) => x.value === currentState.primaryAddress.cityId
                  )}
                  placeholder="Select your town"
                  options={cities}
                  hasError={Boolean(errors.cityId)}
                  onOptionChange={(v) => handleOptionChange('cityId', v)}
                />
              }
              rules={SELECT_TOWN_VALIDATIONS}
            />
          </div>

          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Your address"
              id="register-address1"
              name="line"
              rules={ADDRESS_VALIDATIONS}
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item mb-5">
            <FormInput
              control={control}
              name="number"
              customComponent={
                <div className="ml-2">
                  <PhoneInput
                    value={currentState.phone.number}
                    defaultCountry={
                      countries.mapIdToAlpha2[currentState.phone.country] ||
                      DEFAULT_COUNTRY
                    }
                    countryCallingCodeEditable={false}
                    countries={countries.list}
                    placeholder="Enter phone number"
                    onChange={(v) => handleOptionChange('number', v)}
                    onCountryChange={(v) => handleOptionChange('country', v)}
                  />
                </div>
              }
              rules={PHONE_NUMBER_VALIDATIONS}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-item d-flex align-content-center justify-content-between">
            <button
              type="submit"
              className="button medium primary"
              style={{ width: '45%' }}
              data-button-type={StepperButtonEnum.PREVIOUS}>
              Previous
            </button>
            <button
              type="submit"
              className="button medium primary"
              style={{ width: '45%' }}
              data-button-type={StepperButtonEnum.SUBMIT}>
              Submit
            </button>
          </div>
        </div>
      </form>
    </>
  );
}

export default RegisterFormAdditional;
