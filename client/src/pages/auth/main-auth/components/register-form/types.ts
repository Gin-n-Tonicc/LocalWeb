export enum StepperButtonEnum {
  PREVIOUS = 'PREVIOUS',
  NEXT = 'NEXT',
  SUBMIT = 'SUBMIT',
}

export enum StepperEnum {
  GENERAL,
  ADDITIONAL_INFO,
  __LENGTH,
}

export interface IStepperState {
  currentStep: StepperEnum;
  [StepperEnum.GENERAL]: IGeneralStepper;
  [StepperEnum.ADDITIONAL_INFO]: IAdditionalStepper;
}

export interface IGeneralStepper {
  email: string;
  name: string;
  surname: string;
  password: string;
  repeatPassword: string;
}

export interface IGeneralStepperForm extends IGeneralStepper {}

export interface IAdditionalStepper {
  primaryAddress: {
    line: string;
    cityId: string;
  };
  phone: {
    country: string;
    number: string;
  };
}

export type AdditionalStepperForm = IAdditionalStepper['primaryAddress'] &
  IAdditionalStepper['phone'];

export type PossibleStepperDTO = IGeneralStepper | IAdditionalStepper;
