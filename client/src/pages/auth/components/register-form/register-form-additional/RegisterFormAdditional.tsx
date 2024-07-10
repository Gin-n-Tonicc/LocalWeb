import { useState } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import PhoneInput from 'react-phone-number-input';
import 'react-phone-number-input/style.css';
import FormInput from '../../../../../components/common/form-input/FormInput';
import SelectInput, {
  SelectOption,
} from '../../../../../components/common/select-input/SelectInput';
import { ICity } from '../../../../../types/interfaces/location/ICity';
import { ICountry } from '../../../../../types/interfaces/location/ICountry';
import { AdditionalStepperForm } from '../types';

interface RegisterFormAdditionalProps {
  cities: ICity[];
  countries: ICountry[];
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

  // Handle form submission
  const onSubmit: SubmitHandler<AdditionalStepperForm> = async (data) => {};

  const cities: SelectOption[] = props.cities.map((x) => ({
    value: x.id,
    label: `${x.name}, ${x.country.name}`,
  }));

  const countries: SelectOption[] = props.countries.map((x) => ({
    value: x.phoneCode,
    label: x.name,
  }));

  return (
    <>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <div className="form-row">
          <div className="form-item mb-2">
            <SelectInput
              placeholder="Select your town"
              options={cities}
              onOptionChange={() => {}}
            />
          </div>
          <PhoneInput
            placeholder="Enter phone number"
            value={value}
            onChange={setValue2}
          />
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
          <div className="form-item mb-2">
            <SelectInput
              placeholder="Select your country"
              options={countries}
              onOptionChange={() => {}}
            />
          </div>
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Your city 2"
              id="register-city2"
              name="cityId2"
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-item d-flex align-content-center justify-content-end">
            <button className="button medium primary" style={{ width: '50%' }}>
              Next
            </button>
          </div>
        </div>
      </form>
    </>
  );
}

export default RegisterFormAdditional;
