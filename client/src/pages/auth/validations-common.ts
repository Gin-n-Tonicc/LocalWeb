import { RegisterOptions } from 'react-hook-form';

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
