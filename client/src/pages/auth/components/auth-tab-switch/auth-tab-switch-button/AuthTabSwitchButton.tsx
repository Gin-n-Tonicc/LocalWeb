import { useLocation, useNavigate } from 'react-router-dom';
import { PageEnum } from '../../../../../types/enums/PageEnum';

interface AuthTabSwitchButtonProps {
  path: PageEnum;
  text: string;
}

function AuthTabSwitchButton(props: AuthTabSwitchButtonProps) {
  const location = useLocation();
  const navigate = useNavigate();

  const handleNavigate = () => {
    navigate(props.path);
  };

  let className = 'tab-switch-button login-register-form-trigger';
  if (location.pathname === props.path) {
    className += ' active';
  }

  return (
    <p onClick={handleNavigate} className={className}>
      {props.text}
    </p>
  );
}

export default AuthTabSwitchButton;
