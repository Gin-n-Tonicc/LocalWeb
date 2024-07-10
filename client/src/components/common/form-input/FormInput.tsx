import { FocusEventHandler, useState } from 'react';
import { UseControllerProps, useController } from 'react-hook-form';
import FormErrorWrapper from '../form-error-wrapper/FormErrorWrapper';

export type FormInputProps = {
  type: string;
  id: string;
  placeholder?: string;
} & UseControllerProps<any>;

enum FormInputStateClasses {
  ONFOCUS = 'active',
  ONBLUR = '',
}

// Custom form input attached to the controller (form)
function FormInput(props: FormInputProps) {
  const { field, fieldState } = useController(props);
  const [className, setClassName] = useState(() => {
    if (field.value?.toString() === '') {
      return FormInputStateClasses.ONBLUR;
    }

    return FormInputStateClasses.ONFOCUS;
  });

  const onFocus = () => {
    setClassName(FormInputStateClasses.ONFOCUS);
  };

  const onBlur: FocusEventHandler<HTMLInputElement> = (e) => {
    const input = e.currentTarget;

    if (input.value.trim() === '') {
      setClassName(FormInputStateClasses.ONBLUR);
    }
  };

  const inputWrapperClassName = `form-input ${className}`;

  return (
    <FormErrorWrapper message={fieldState.error?.message}>
      <div className={inputWrapperClassName}>
        <label htmlFor={props.id}>{props.placeholder}</label>
        <input
          {...field}
          type={props.type}
          id={props.id}
          name={props.name}
          onFocus={onFocus}
          onBlur={onBlur}
        />
      </div>
    </FormErrorWrapper>
  );
}

export default FormInput;
