import '../Auth.scss';
import AuthTabSwitch from '../components/auth-tab-switch/AuthTabSwitch';
import ForgotPasswordForm from './forgot-password-form/ForgotPasswordForm';

function ForgotPassword() {
  return (
    <>
      <div className="landing">
        <div className="landing-decoration" />
        <div className="landing-info">
          <div className="logo"></div>
          <h2 className="landing-info-pretitle">Welcome to</h2>
          <h1 className="landing-info-title">Local Web</h1>
          <p className="landing-info-text">
            The next generation social network &amp; community! Connect with
            your friends and play with our quests and badges gamification
            system!
          </p>
          <AuthTabSwitch />
        </div>
        <div className="landing-form">
          <ForgotPasswordForm />
        </div>
      </div>
    </>
  );
}

export default ForgotPassword;
