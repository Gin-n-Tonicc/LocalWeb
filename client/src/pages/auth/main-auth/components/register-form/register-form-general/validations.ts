import { RegisterOptions } from 'react-hook-form';
import { PASSWORD_VALIDATIONS } from '../../../../validations-common';

export const REPEAT_PASSWORD_VALIDATIONS = (
  password: string
): RegisterOptions => ({
  ...PASSWORD_VALIDATIONS,
  validate: (val: string) => {
    if (password !== val) {
      return 'auth.repeat.password.not.match';
    }
  },
});

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
