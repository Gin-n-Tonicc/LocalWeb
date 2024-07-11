import { RegisterOptions } from 'react-hook-form';
import { isValidPhoneNumber } from 'react-phone-number-input';

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
