import { useLocation } from 'react-router-dom';
import { PageEnum } from '../../types/enums/PageEnum';
import './Auth.scss';
import AuthTabSwitch from './components/auth-tab-switch/AuthTabSwitch';
import LoginForm from './components/login-form/LoginForm';
import RegisterForm from './components/register-form/RegisterForm';

const pathComponentMap = new Map<string, JSX.Element>([
  [PageEnum.LOGIN, <LoginForm />],
  [PageEnum.REGISTER, <RegisterForm />],
]);

function Auth() {
  const location = useLocation();

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
          {pathComponentMap.get(location.pathname)}
        </div>
      </div>
    </>
  );
}

export default Auth;
