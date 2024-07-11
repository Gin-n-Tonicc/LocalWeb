import { SubmitHandler, useForm } from 'react-hook-form';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useFetch } from 'use-http';
import { authUrls } from '../../../../api/auth/auth';
import FormInput from '../../../../components/common/form-input/FormInput';
import { useToastContext } from '../../../../contexts/ToastContext';
import { PageEnum } from '../../../../types/enums/PageEnum';
import rocketImage from '../../img/rocket.png';
import {
  EMAIL_VALIDATIONS,
  PASSWORD_VALIDATIONS,
} from '../../validations-common';

type Inputs = {
  email: string;
  newPassword: string;
};

function ForgotPasswordForm() {
  const navigate = useNavigate();
  const { info, success } = useToastContext();

  const [searchParams, _] = useSearchParams();
  const token = searchParams.get('token') || '';

  const { control, reset, watch, handleSubmit } = useForm<Inputs>({
    defaultValues: {
      email: '',
      newPassword: '',
    },
    mode: 'onSubmit',
  });

  const { post: forgotPasswordPost, response: forgotPasswordRes } =
    useFetch<string>(authUrls.forgotPassword(watch('email')));

  const { post: resetPasswordPost, response: resetPasswordRes } =
    useFetch<string>(authUrls.resetPassword(token, watch('newPassword')));

  const buttonText = token ? 'Reset password' : 'Send reset email';

  const onSubmit: SubmitHandler<Inputs> = async () => {
    if (!token) {
      handleEmailSend();
    } else {
      handlePasswordChange();
    }
  };

  const handleEmailSend = async () => {
    await forgotPasswordPost();

    if (forgotPasswordRes.ok) {
      reset();
      info('Password reset link has been sent to your email!');
    }
  };

  const handlePasswordChange = async () => {
    await resetPasswordPost();

    if (resetPasswordRes.ok) {
      reset();
      success('Password reset successfully!');
      navigate(PageEnum.LOGIN);
    }
  };

  return (
    <div className="form-box login-register-form-element login-form">
      <img
        className="form-box-decoration overflowing"
        src={rocketImage}
        alt="rocket"
      />
      <h2 className="form-box-title">Reset Password</h2>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <div className="form-row">
          <div className="form-item">
            {!token ? (
              <FormInput
                control={control}
                type="text"
                placeholder="Email"
                id="forgot-password-email"
                name="email"
                rules={EMAIL_VALIDATIONS}
              />
            ) : (
              <FormInput
                control={control}
                type="password"
                placeholder="New Password"
                id="forgot-password-password"
                name="newPassword"
                rules={PASSWORD_VALIDATIONS}
              />
            )}
          </div>
        </div>
        <div className="form-row">
          <div className="form-item"></div>
        </div>
        <div className="form-row">
          <div className="form-item">
            <button className="button medium secondary">{buttonText}</button>
          </div>
        </div>
      </form>
    </div>
  );
}

export default ForgotPasswordForm;
