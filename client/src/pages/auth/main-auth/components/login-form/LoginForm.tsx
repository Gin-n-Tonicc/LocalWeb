import { SubmitHandler, useForm } from 'react-hook-form';
import { Link, useNavigate } from 'react-router-dom';
import { useFetch } from 'use-http';
import { oauth2Urls } from '../../../../../api';
import { authUrls } from '../../../../../api/auth/auth';
import FormInput from '../../../../../components/common/form-input/FormInput';
import { useAuthContext } from '../../../../../contexts/AuthContext';
import { PageEnum } from '../../../../../types/enums/PageEnum';
import { IAuthResponse } from '../../../../../types/interfaces/auth/IAuthResponse';
import rocketImage from '../../../img/rocket.png';
import {
  EMAIL_VALIDATIONS,
  PASSWORD_VALIDATIONS,
} from '../../../validations-common';
import googleIcon from './img/icons/google.svg';

type Inputs = {
  email: string;
  password: string;
};

function LoginForm() {
  const navigate = useNavigate();
  const { loginUser } = useAuthContext();

  // Prepare fetches
  const { post, response } = useFetch<IAuthResponse>(authUrls.login);

  const { control, reset, handleSubmit } = useForm<Inputs>({
    defaultValues: {
      email: '',
      password: '',
    },
    mode: 'onChange',
  });

  const onSubmit: SubmitHandler<Inputs> = async (data) => {
    const user = await post({
      email: data.email.trim(),
      password: data.password.trim(),
    });

    // Reset form, update auth state (login user) and navigate
    if (response.ok) {
      reset();
      loginUser(user);
      navigate(PageEnum.HOME);
    }
  };

  return (
    <div className="form-box login-register-form-element login-form">
      <img
        className="form-box-decoration overflowing"
        src={rocketImage}
        alt="rocket"
      />
      <h2 className="form-box-title">Login</h2>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Email"
              id="login-email"
              name="email"
              rules={EMAIL_VALIDATIONS}
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
              rules={PASSWORD_VALIDATIONS}
            />
          </div>
        </div>
        <div className="form-row space-between">
          <div className="form-item"></div>
          <div className="form-item">
            <Link className="form-link" to={PageEnum.FORGOT_PASSWORD}>
              Forgot Password?
            </Link>
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
