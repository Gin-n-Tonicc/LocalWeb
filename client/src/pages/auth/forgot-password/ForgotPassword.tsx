import AuthTabSwitch from '../components/auth-tab-switch/AuthTabSwitch';

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
          {/* {pathComponentMap.get(location.pathname)} */}
        </div>
      </div>
    </>
  );
}

export default ForgotPassword;
