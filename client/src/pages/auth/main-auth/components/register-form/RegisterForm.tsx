import { useMemo, useState } from 'react';
import { useFetch } from 'use-http';
import { authUrls } from '../../../../../api/auth/auth';
import { cityUrls } from '../../../../../api/location/city';
import { countryUrls } from '../../../../../api/location/country';
import { useToastContext } from '../../../../../contexts/ToastContext';
import { IAuthResponse } from '../../../../../types/interfaces/auth/IAuthResponse';
import { ICity } from '../../../../../types/interfaces/location/ICity';
import { ICountry } from '../../../../../types/interfaces/location/ICountry';
import rocketImage from '../../../img/rocket.png';
import RegisterFormAdditional from './register-form-additional/RegisterFormAdditional';
import RegisterFormGeneral from './register-form-general/RegisterFormGeneral';
import { IStepperState, PossibleStepperDTO, StepperEnum } from './types';

const DEFAULT_STEPPER_STATE: IStepperState = {
  currentStep: 0,
  [StepperEnum.GENERAL]: {
    email: '',
    name: '',
    surname: '',
    password: '',
    repeatPassword: '',
  },
  [StepperEnum.ADDITIONAL_INFO]: {
    primaryAddress: {
      line: '',
      cityId: '',
    },
    phone: {
      country: '',
      number: '',
    },
  },
};

function RegisterForm() {
  const [stepperState, setStepperState] = useState(DEFAULT_STEPPER_STATE);
  const { info } = useToastContext();

  const { data: cities } = useFetch<ICity[]>(cityUrls.fetchAll, []);
  const { data: countries } = useFetch<ICountry[]>(countryUrls.fetchAll, []);
  const { post: postUser, response: responseUser } = useFetch<IAuthResponse>(
    authUrls.register
  );

  const handleSubmit = async (data: PossibleStepperDTO) => {
    const stepperValue = { ...stepperState, [stepperState.currentStep]: data };
    const { repeatPassword, ...generalStepperValue } =
      stepperValue[StepperEnum.GENERAL];

    const body = {
      ...generalStepperValue,
      ...stepperValue[StepperEnum.ADDITIONAL_INFO],
    };

    await postUser(body);
    if (responseUser.ok) {
      setStepperState(DEFAULT_STEPPER_STATE);
      info('A confirmation email has been sent to the given email address!');
    }
  };

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
        [
          StepperEnum.GENERAL,
          <RegisterFormGeneral
            nextStep={nextStep}
            currentState={stepperState[StepperEnum.GENERAL]}
          />,
        ],
        [
          StepperEnum.ADDITIONAL_INFO,
          <RegisterFormAdditional
            cities={cities || []}
            countries={countries || []}
            previousStep={previousStep}
            submit={handleSubmit}
            currentState={stepperState[StepperEnum.ADDITIONAL_INFO]}
          />,
        ],
      ]),
    [stepperState, countries, cities, nextStep, previousStep, handleSubmit]
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
