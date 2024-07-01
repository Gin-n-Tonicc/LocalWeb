import { PageEnum } from '../../../../types/enums/PageEnum';
import AuthTabSwitchButton from './auth-tab-switch-button/AuthTabSwitchButton';

function AuthTabSwitch() {
  return (
    <div className="tab-switch">
      <AuthTabSwitchButton path={PageEnum.LOGIN} text="Login" />
      <AuthTabSwitchButton path={PageEnum.REGISTER} text="Register" />
    </div>
  );
}

export default AuthTabSwitch;
