import { PropsWithChildren } from 'react';
import './FormErrorWrapper.scss';

interface FormErrorWrapperProps extends PropsWithChildren {
  message: string | undefined;
}

// The component that displays an error below the given children
function FormErrorWrapper(props: FormErrorWrapperProps) {
  return (
    <div className="FormErrorWrapper-container">
      {props.children}
      <p className="FormErrorWrapper-error">{props.message}</p>
    </div>
  );
}

export default FormErrorWrapper;
