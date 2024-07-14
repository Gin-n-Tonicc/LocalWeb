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
