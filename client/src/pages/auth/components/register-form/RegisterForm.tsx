import { useMemo, useState } from 'react';
import { useFetch } from 'use-http';
import { cityUrls } from '../../../../api/location/city';
import { countryUrls } from '../../../../api/location/country';
import { ICity } from '../../../../types/interfaces/location/ICity';
import { ICountry } from '../../../../types/interfaces/location/ICountry';
import rocketImage from '../../img/rocket.png';
import RegisterFormAdditional from './register-form-additional/RegisterFormAdditional';
import RegisterFormGeneral from './register-form-general/RegisterFormGeneral';
import { IStepperState, PossibleStepperDTO, StepperEnum } from './types';

function RegisterForm() {
  const [stepperState, setStepperState] = useState<IStepperState>({
    currentStep: 0,
  });

  const { data: cities } = useFetch<ICity[]>(cityUrls.fetchAll, []);
  const { data: countries } = useFetch<ICountry[]>(countryUrls.fetchAll, []);

  // Handle form submission
  // const onSubmit: SubmitHandler<Inputs> = async (data) => {
  // Register user
  // await post({
  //   name: data.firstName.trim(),
  //   surname: data.surName.trim(),
  //   email: data.email.trim(),
  //   password: data.password.trim(),
  // });
  // Reset form, show a message that an email is sent
  // if (response.ok) {
  //   reset();
  //   // TODO: Identify user that an email has been sent to him to verify his account
  // }
  // };

  // useEffect(() => {
  //   const areEqual = formValues.password === formValues.repeatPassword;
  //   const hasError = Boolean(errors.repeatPassword);
  //   const hasManualError = hasError && errors.repeatPassword?.type === 'manual';

  //   if (!hasError && !areEqual) {
  //     setError('repeatPassword', {
  //       type: 'manual',
  //       message: 'Passwords did not match',
  //     });
  //   }

  //   if (hasManualError && areEqual) {
  //     clearErrors('repeatPassword');
  //   }
  // }, [errors, formValues, setError, clearErrors]);

  console.log(stepperState);

  const handleStep = (data: PossibleStepperDTO, step: number) => {
    setStepperState((prev) => {
      let updatedStep = prev.currentStep + step;

      if (updatedStep < 0) {
        updatedStep = 0;
      } else if (updatedStep >= StepperEnum.__LENGTH) {
        updatedStep = StepperEnum.__LENGTH - 1;
      }

      return {
        ...prev,
        [prev.currentStep]: data,
        currentStep: updatedStep,
      };
    });
  };

  const previousStep = (data: PossibleStepperDTO) => {
    handleStep(data, -1);
  };

  const nextStep = (data: PossibleStepperDTO) => {
    handleStep(data, 1);
  };

  const stepperEnumToComponentMap = useMemo(
    () =>
      new Map<number, JSX.Element>([
        [StepperEnum.GENERAL, <RegisterFormGeneral nextStep={nextStep} />],
        [
          StepperEnum.ADDITIONAL_INFO,
          <RegisterFormAdditional
            cities={cities || []}
            countries={countries || []}
            previousStep={previousStep}
          />,
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
