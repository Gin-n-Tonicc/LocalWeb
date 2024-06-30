import { useForm } from 'react-hook-form';
import FormInput from '../../../../components/form-input/FormInput';
import rocketImage from '../../img/rocket.png';

type Inputs = {
  firstName: string;
  email: string;
  password: string;
  repeatPassword: string;
};

function RegisterForm() {
  const { control } = useForm<Inputs>({
    defaultValues: {
      firstName: '',
      email: '',
      password: '',
      repeatPassword: '',
    },
    mode: 'onChange',
  });

  return (
    <div className="form-box login-register-form-element register-form">
      <img className="form-box-decoration" src={rocketImage} alt="rocket" />
      <h2 className="form-box-title">Create your Account!</h2>
      <form className="form">
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
