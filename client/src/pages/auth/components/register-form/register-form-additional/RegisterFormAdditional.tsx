import { SubmitHandler, useForm } from 'react-hook-form';
import FormInput from '../../../../../components/common/form-input/FormInput';
import SelectInput from '../../../../../components/common/select-input/SelectInput';
import { ICity } from '../../../../../types/interfaces/location/ICity';
import { AdditionalStepperForm } from '../types';

interface RegisterFormAdditionalProps {
  cities: ICity[];
}

function RegisterFormAdditional(props: RegisterFormAdditionalProps) {
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

  return (
    <>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <div className="form-row">
          <div className="form-item mb-2">
            <SelectInput
              placeholder="Select your town"
              options={props.cities.map((x) => ({
                value: x.id,
                label: `${x.name}, ${x.country.name}`,
              }))}
              onOptionChange={() => {}}
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
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Your address 2"
              id="register-address2"
              name="line2"
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
