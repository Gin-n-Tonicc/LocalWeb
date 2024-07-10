import { useEffect } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import FormInput from '../../../../../components/common/form-input/FormInput';
import {
  IGeneralStepper,
  IGeneralStepperForm,
  StepperButtonEnum,
} from '../types';

interface RegisterFormGeneralProps {
  currentState: IGeneralStepper;
  nextStep: (v: IGeneralStepper) => void;
}

function RegisterFormGeneral({
  currentState,
  nextStep,
}: RegisterFormGeneralProps) {
  const {
    handleSubmit,
    control,
    reset,
    watch,
    setError,
    clearErrors,
    setValue,
    formState: { errors },
  } = useForm<IGeneralStepperForm>({
    defaultValues: {
      name: currentState.name,
      surname: currentState.surname,
      email: currentState.email,
      password: currentState.password,
      repeatPassword: currentState.repeatPassword,
    },
    mode: 'onChange',
  });

  const formValues = watch();

  useEffect(() => {
    const areEqual = formValues.password === formValues.repeatPassword;
    const hasError = Boolean(errors.repeatPassword);
    const hasManualError = hasError && errors.repeatPassword?.type === 'manual';

    if (!hasError && !areEqual) {
      setError('repeatPassword', {
        type: 'manual',
        message: 'Passwords did not match',
      });
    }

    if (hasManualError && areEqual) {
      clearErrors('repeatPassword');
    }
  }, [errors, formValues, setError, clearErrors]);

  // Handle form submission
  const onSubmit: SubmitHandler<IGeneralStepperForm> = async (data) => {
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
    <>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Your Email"
              id="register-email"
              name="email"
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
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item d-flex align-content-center justify-content-end">
            <button
              className="button medium primary"
              style={{ width: '45%' }}
              data-button-type={StepperButtonEnum.NEXT}>
              Next
            </button>
          </div>
        </div>
      </form>
    </>
  );
}

export default RegisterFormGeneral;
