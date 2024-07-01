import { useEffect } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useFetch } from 'use-http';
import { authUrls } from '../../../../api/auth/auth';
import FormInput from '../../../../components/common/form-input/FormInput';
import { IUser } from '../../../../types/interfaces/auth/IUser';
import rocketImage from '../../img/rocket.png';

type Inputs = {
  firstName: string;
  surName: string;
  email: string;
  password: string;
  repeatPassword: string;
};

function RegisterForm() {
  const { post, response, loading } = useFetch<IUser>(authUrls.register);

  const {
    handleSubmit,
    control,
    reset,
    watch,
    setError,
    clearErrors,
    formState: { errors },
  } = useForm<Inputs>({
    defaultValues: {
      firstName: '',
      surName: '',
      email: '',
      password: '',
      repeatPassword: '',
    },
    mode: 'onChange',
  });

  const formValues = watch();

  // Handle form submission
  const onSubmit: SubmitHandler<Inputs> = async (data) => {
    // Register user
    await post({
      name: data.firstName.trim(),
      surname: data.surName.trim(),
      email: data.email.trim(),
      password: data.password.trim(),
    });

    // Reset form, show a message that an email is sent
    if (response.ok) {
      reset();
      // TODO: Identify user that an email has been sent to him to verify his account
    }
  };

  useEffect(() => {
    const areEqual = formValues.password === formValues.repeatPassword;
    const hasError = Boolean(errors.repeatPassword);
    const hasManualError = hasError && errors.repeatPassword?.type === 'manual';

    if (!hasError && !areEqual) {
      setError('repeatPassword', {
        type: 'manual',
        message: 'Passwords did not match',
      });
    }

    if (hasManualError && areEqual) {
      clearErrors('repeatPassword');
    }
  }, [errors, formValues, setError, clearErrors]);

  return (
    <div className="form-box login-register-form-element register-form">
      <img className="form-box-decoration" src={rocketImage} alt="rocket" />
      <h2 className="form-box-title">Create your Account!</h2>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Your Email"
              id="register-email"
              name="email"
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="First Name"
              id="register-first-name"
              name="firstName"
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Sur Name"
              id="register-sur-name"
              name="surName"
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="password"
              placeholder="Password"
              id="register-password"
              name="password"
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="password"
              placeholder="Repeat Password"
              id="login-repeat-password"
              name="repeatPassword"
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item">
            <button className="button medium primary">Register Now!</button>
          </div>
        </div>
      </form>
      <p className="form-text">
        You'll receive a confirmation email in your inbox with a link to
        activate your account. If you have any problems,{' '}
        <a href="#">contact us</a>!
      </p>
    </div>
  );
}

export default RegisterForm;
