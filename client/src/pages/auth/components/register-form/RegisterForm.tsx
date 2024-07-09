import { useEffect, useMemo, useState } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useFetch } from 'use-http';
import { cityUrls } from '../../../../api/address/city';
import { authUrls } from '../../../../api/auth/auth';
import { IUser } from '../../../../types/interfaces/auth/IUser';
import { ICity } from '../../../../types/interfaces/location/ICity';
import rocketImage from '../../img/rocket.png';
import RegisterFormAdditional from './register-form-additional/RegisterFormAdditional';
import RegisterFormGeneral from './register-form-general/RegisterFormGeneral';
import { IGeneralStepper, PossibleStepperDTO, StepperEnum } from './types';

type Inputs = { repeatPassword: string; password: string };

interface IStepperState {
  currentStep: StepperEnum;
  [StepperEnum.GENERAL]?: IGeneralStepper;
  [StepperEnum.ADDITIONAL_INFO]?: {};
}

function RegisterForm() {
  const [stepperState, setStepperState] = useState<IStepperState>({
    currentStep: 0,
  });

  const { data: cities } = useFetch<ICity[]>(cityUrls.fetchAll, []);
  console.log(cities);

  const { post, response, loading } = useFetch<IUser>(authUrls.register);

  const {
    handleSubmit,
    control,
    reset,
    watch,
    setError,
    clearErrors,
    setValue,
    formState: { errors },
  } = useForm<Inputs>({
    defaultValues: {
      password: '',
      repeatPassword: '',
    },
    mode: 'onChange',
  });

  const formValues = watch();

  // Handle form submission
  const onSubmit: SubmitHandler<Inputs> = async (data) => {
    // Register user
    // await post({
    //   name: data.firstName.trim(),
    //   surname: data.surName.trim(),
    //   email: data.email.trim(),
    //   password: data.password.trim(),
    // });

    // Reset form, show a message that an email is sent
    if (response.ok) {
      reset();
      // TODO: Identify user that an email has been sent to him to verify his account
    }
  };

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

  console.log(stepperState);

  const handleStep = (data: PossibleStepperDTO) => {
    if (
      stepperState.currentStep >= 0 &&
      stepperState.currentStep < StepperEnum.__LENGTH - 1
    ) {
      setStepperState((prev) => ({
        ...prev,
        [prev.currentStep]: data,
        currentStep: prev.currentStep + 1,
      }));
    } else {
      setStepperState((prev) => ({
        ...prev,
        [prev.currentStep]: data,
        currentStep: prev.currentStep - 1,
      }));
    }
  };

  const stepperEnumToComponentMap = useMemo(
    () =>
      new Map<number, JSX.Element>([
        [StepperEnum.GENERAL, <RegisterFormGeneral handleStep={handleStep} />],
        [
          StepperEnum.ADDITIONAL_INFO,
          <RegisterFormAdditional cities={cities || []} />,
        ],
      ]),
    [handleStep]
  );

  return (
    <div className="form-box login-register-form-element register-form">
      <img className="form-box-decoration" src={rocketImage} alt="rocket" />
      <h2 className="form-box-title">Create your Account!</h2>

      {stepperEnumToComponentMap.get(stepperState.currentStep)}

      <p className="form-text">
        You'll receive a confirmation email in your inbox with a link to
        activate your account. If you have any problems,{' '}
        <a href="#">contact us</a>!
      </p>
    </div>
  );
}

export default RegisterForm;
