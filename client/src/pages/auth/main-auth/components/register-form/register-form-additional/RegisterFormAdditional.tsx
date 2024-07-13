import { useMemo } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Country } from 'react-phone-number-input';
import 'react-phone-number-input/style.css';
import FormInput from '../../../../../../components/common/form-input/FormInput';
import { ICity } from '../../../../../../types/interfaces/location/ICity';
import { ICountry } from '../../../../../../types/interfaces/location/ICountry';
import CitySelect from '../../../../components/city-select/CitySelect';
import PhoneInput, {
  ICountriesReduced,
  reduceCountries,
} from '../../../../components/phone-input/PhoneInput';
import {
  ADDRESS_VALIDATIONS,
  PHONE_NUMBER_VALIDATIONS,
  SELECT_TOWN_VALIDATIONS,
} from '../../../../validations-common';
import {
  AdditionalStepperForm,
  IAdditionalStepper,
  StepperButtonEnum,
} from '../types';

interface RegisterFormAdditionalProps {
  currentState: IAdditionalStepper;
  cities: ICity[];
  countries: ICountry[];
  countriesReduced: ICountriesReduced;
  previousStep: (v: IAdditionalStepper) => void;
  submit: (v: IAdditionalStepper) => void;
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

  const countries = useMemo(
    () => reduceCountries(props.countries),
    [props.countries]
  );

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
                <CitySelect
                  cities={props.cities}
                  error={errors.cityId}
                  idToFindBy={currentState.primaryAddress.cityId}
                  keyToSetValueTo="cityId"
                  setValue={setValue}
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
                <PhoneInput
                  placeholder="Enter phone number"
                  currentNumber={currentState.phone.number}
                  currentCountry={currentState.phone.country}
                  countries={countries}
                  keyToSetNumber="number"
                  keyToSetCountry="country"
                  setValue={setValue}
                />
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
