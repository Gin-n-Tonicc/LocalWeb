import { useState } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import PhoneInput, { Country } from 'react-phone-number-input';
import 'react-phone-number-input/style.css';
import FormInput from '../../../../../components/common/form-input/FormInput';
import SelectInput, {
  SelectOption,
} from '../../../../../components/common/select-input/SelectInput';
import { ICity } from '../../../../../types/interfaces/location/ICity';
import { ICountry } from '../../../../../types/interfaces/location/ICountry';
import { AdditionalStepperForm, IAdditionalStepper } from '../types';

interface RegisterFormAdditionalProps {
  cities: ICity[];
  countries: ICountry[];
  previousStep: (v: IAdditionalStepper) => void;
}

function RegisterFormAdditional(props: RegisterFormAdditionalProps) {
  const [value, setValue2] = useState<any>('');

  const {
    handleSubmit,
    control,
    reset,
    watch,
    setError,
    clearErrors,
    setValue,
    formState: { errors },
  } = useForm<AdditionalStepperForm>({
    defaultValues: {
      line: '',
      cityId: '',
      country: '',
      number: '',
    },
    mode: 'onChange',
  });

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
  const onSubmit: SubmitHandler<AdditionalStepperForm> = async (data) => {
    const dataFinish: IAdditionalStepper = {
      primaryAddress: {
        line: data.line.trim(),
        cityId: data.cityId.trim(),
      },
      phone: {
        country: '',
        number: data.number,
      },
    };

    console.log(dataFinish);

    props.previousStep(dataFinish);
  };

  const cities: SelectOption[] = props.cities.map((x) => ({
    value: x.id,
    label: `${x.name}, ${x.country.name}`,
  }));

  const countries: Country[] = props.countries.map((x) => x.alpha2 as Country);

  return (
    <>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <div className="form-row">
          <div className="form-item mb-2">
            <SelectInput
              placeholder="Select your town"
              options={cities}
              onOptionChange={(v) => handleOptionChange('cityId', v)}
            />
          </div>

          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Your address"
              id="register-address1"
              name="line"
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item ml-2 mb-5">
            <PhoneInput
              defaultCountry="BG"
              countryCallingCodeEditable={false}
              countries={countries}
              placeholder="Enter phone number"
              onChange={(v) => handleOptionChange('number', v)}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-item d-flex align-content-center justify-content-between">
            <button className="button medium primary" style={{ width: '45%' }}>
              Previous
            </button>
            <button className="button medium primary" style={{ width: '45%' }}>
              Submit
            </button>
          </div>
        </div>
      </form>
    </>
  );
}

export default RegisterFormAdditional;
