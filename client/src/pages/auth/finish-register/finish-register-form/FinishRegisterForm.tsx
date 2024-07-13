import { SubmitHandler, useForm } from 'react-hook-form';
import FormInput from '../../../../components/common/form-input/FormInput';
import { useLocationContext } from '../../../../contexts/LocationContext';
import CitySelect from '../../components/city-select/CitySelect';
import PhoneInput from '../../components/phone-input/PhoneInput';
import rocketImage from '../../img/rocket.png';
import {
  ADDRESS_VALIDATIONS,
  FIRST_NAME_VALIDATIONS,
  PHONE_NUMBER_VALIDATIONS,
  SELECT_TOWN_VALIDATIONS,
  SUR_NAME_VALIDATIONS,
} from '../../validations-common';

type FinishRegisterFormTypes = {
  name: string;
  surname: string;
  cityId: string;
  line: string;
  number: string;
  country: string;
};

function FinishRegisterForm() {
  const {
    handleSubmit,
    control,
    setValue,
    watch,
    formState: { errors },
  } = useForm<FinishRegisterFormTypes>({
    defaultValues: {
      name: '',
      surname: '',
      cityId: '',
      line: '',
      number: '',
      country: '',
    },
    mode: 'onSubmit',
  });

  const { cities, countries, countriesReduced } = useLocationContext();

  const onSubmit: SubmitHandler<FinishRegisterFormTypes> = (v) => {};

  return (
    <div className="form-box login-register-form-element login-form">
      <img
        className="form-box-decoration overflowing"
        src={rocketImage}
        alt="rocket"
      />
      <h2 className="form-box-title">Finish Register</h2>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="First Name"
              id="register-first-name"
              name="name"
              rules={FIRST_NAME_VALIDATIONS}
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Sur Name"
              id="register-sur-name"
              name="surname"
              rules={SUR_NAME_VALIDATIONS}
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item mb-4">
            <FormInput
              control={control}
              name="cityId"
              customComponent={
                <CitySelect
                  cities={cities}
                  error={errors.cityId}
                  idToFindBy={''}
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
                  currentNumber={watch('number')}
                  currentCountry={watch('country')}
                  countries={countriesReduced}
                  keyToSetNumber="number"
                  keyToSetCountry="country"
                  setValue={setValue}
                />
              }
              rules={PHONE_NUMBER_VALIDATIONS}
            />
          </div>
        </div>
        <div className="form-row"></div>
        <div className="form-row">
          <div className="form-item">
            <button className="button medium secondary">Register</button>
          </div>
        </div>
      </form>
    </div>
  );
}

export default FinishRegisterForm;
