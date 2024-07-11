import { SubmitHandler, useForm } from 'react-hook-form';
import FormInput from '../../../../../components/common/form-input/FormInput';
import {
  IGeneralStepper,
  IGeneralStepperForm,
  StepperButtonEnum,
} from '../types';
import {
  EMAIL_VALIDATIONS,
  FIRST_NAME_VALIDATIONS,
  PASSWORD_VALIDATIONS,
  REPEAT_PASSWORD_VALIDATIONS,
  SUR_NAME_VALIDATIONS,
} from './validations';

interface RegisterFormGeneralProps {
  currentState: IGeneralStepper;
  nextStep: (v: IGeneralStepper) => void;
}

function RegisterFormGeneral({
  currentState,
  nextStep,
}: RegisterFormGeneralProps) {
  const { handleSubmit, control, watch } = useForm<IGeneralStepperForm>({
    defaultValues: {
      name: currentState.name,
      surname: currentState.surname,
      email: currentState.email,
      password: currentState.password,
      repeatPassword: currentState.repeatPassword,
    },
    mode: 'onSubmit',
  });

  // Handle form submission
  const onSubmit: SubmitHandler<IGeneralStepperForm> = async (
    data: IGeneralStepperForm
  ) => {
    const dataFinish: IGeneralStepper = {
      name: data.name.trim(),
      surname: data.surname.trim(),
      email: data.email.trim(),
      password: data.password.trim(),
      repeatPassword: data.repeatPassword.trim(),
    };

    nextStep(dataFinish);
  };

  return (
    <form className="form" onSubmit={handleSubmit(onSubmit)}>
      <div className="form-row">
        <div className="form-item">
          <FormInput
            control={control}
            type="text"
            placeholder="Your Email"
            id="register-email"
            name="email"
            rules={EMAIL_VALIDATIONS}
          />
        </div>
      </div>
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
        <div className="form-item">
          <FormInput
            control={control}
            type="password"
            placeholder="Password"
            id="register-password"
            name="password"
            rules={PASSWORD_VALIDATIONS}
          />
        </div>
      </div>
      <div className="form-row">
        <div className="form-item">
          <FormInput
            control={control}
            type="password"
            placeholder="Repeat Password"
            id="login-repeat-password"
            name="repeatPassword"
            rules={REPEAT_PASSWORD_VALIDATIONS(watch('password'))}
          />
        </div>
      </div>
      <div className="form-row">
        <div className="form-item d-flex align-content-center justify-content-end">
          <button
            className="button medium primary mt-5"
            type="submit"
            style={{ width: '45%' }}
            data-button-type={StepperButtonEnum.NEXT}>
            Next
          </button>
        </div>
      </div>
    </form>
  );
}

export default RegisterFormGeneral;
