import { useForm } from 'react-hook-form';
import { oauth2Urls } from '../../../../api';
import FormInput from '../../../../components/form-input/FormInput';
import rocketImage from '../../img/rocket.png';
import facebookIcon from './img/icons/facebook.svg';
import googleIcon from './img/icons/google.svg';

type Inputs = {
  email: string;
  password: string;
};

function LoginForm() {
  const { control } = useForm<Inputs>({
    defaultValues: {
      email: '',
      password: '',
    },
    mode: 'onChange',
  });

  return (
    <div className="form-box login-register-form-element login-form">
      <img
        className="form-box-decoration overflowing"
        src={rocketImage}
        alt="rocket"
      />
      <h2 className="form-box-title">Login</h2>
      <form className="form">
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Email"
              id="login-email"
              name="email"
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="password"
              placeholder="Password"
              id="login-password"
              name="password"
            />
          </div>
        </div>
        <div className="form-row space-between">
          <div className="form-item">
            {/* <div className="checkbox-wrap">
                  <input
                    type="checkbox"
                    id="login-remember"
                    name="login_remember"
                    {...register()}
                  />
                  <div className="checkbox-box">
                    <img src={markIcon} />
                  </div>
                  <label htmlFor="login-remember">Remember Me</label>
                </div> */}
          </div>
          <div className="form-item">
            <a className="form-link" href="#">
              Forgot Password?
            </a>
          </div>
        </div>
        <div className="form-row">
          <div className="form-item">
            <button className="button medium secondary">Login</button>
          </div>
        </div>
      </form>
      <p className="lined-text">Login with your Social Account</p>
      <div className="social-links">
        <a className="social-link facebook" href={oauth2Urls.facebook}>
          <div className="facebook-icon">
            <img src={facebookIcon} />
          </div>
        </a>
        <a className="social-link google" href={oauth2Urls.google}>
          <div className="google-icon">
            <img src={googleIcon} />
          </div>
        </a>
      </div>
    </div>
  );
}

export default LoginForm;
