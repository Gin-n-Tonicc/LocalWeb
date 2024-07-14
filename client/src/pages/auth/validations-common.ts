import { RegisterOptions } from 'react-hook-form';
import { isValidPhoneNumber } from 'react-phone-number-input';

export const EMAIL_VALIDATIONS: RegisterOptions = {
  required: 'auth.email.required',
  pattern: {
    value: /[a-z0-9]+@[a-z]+\.[a-z]{2,3}/gim,
    message: 'auth.email.invalid.pattern',
  },
};

export const PASSWORD_VALIDATIONS: RegisterOptions = {
  required: 'auth.password.required',
  pattern: {
    value: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/gim,
    message: 'auth.password.invalid.pattern',
  },
};

export const FIRST_NAME_VALIDATIONS: RegisterOptions = {
  required: 'auth.first.name.required',
  minLength: {
    value: 2,
    message: 'auth.first.name.invalid.min-length',
  },
};

export const SUR_NAME_VALIDATIONS: RegisterOptions = {
  required: 'auth.sur.name.required',
  minLength: {
    value: 2,
    message: 'auth.sur.name.invalid.min-length',
  },
};

export const SELECT_TOWN_VALIDATIONS: RegisterOptions = {
  required: 'auth.town.required',
};

export const ADDRESS_VALIDATIONS: RegisterOptions = {
  required: 'auth.address.required',
  minLength: {
    value: 5,
    message: 'auth.address.invalid.min-length',
  },
};

export const PHONE_NUMBER_VALIDATIONS: RegisterOptions = {
  required: 'auth.phone.required',
  validate: (v: string) => {
    if (!isValidPhoneNumber(v)) {
      return 'auth.phone.invalid.pattern';
    }
  },
};
