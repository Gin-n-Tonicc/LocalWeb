import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useFetch } from 'use-http';
import { authUrls } from '../../../api/auth/auth';
import Spinner from '../../../components/common/spinner/Spinner';
import { useAuthContext } from '../../../contexts/AuthContext';
import { PageEnum } from '../../../types/enums/PageEnum';

function Logout() {
  const { logoutUser } = useAuthContext();
  const navigate = useNavigate();

  // Logout on mount
  const { loading } = useFetch(authUrls.logout, []);

  // Clear user from state and redirect to home
  useEffect(() => {
    if (!loading) {
      logoutUser();
      navigate(PageEnum.HOME);
    }
  }, [loading]);

  return <Spinner />;
}

export default Logout;
