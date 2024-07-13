import { useCallback } from 'react';
import { Navigate, Outlet, To, useLocation } from 'react-router-dom';
import { useAuthContext } from '../../../contexts/AuthContext';
import { PageEnum } from '../../../types/enums/PageEnum';

interface ProtectedRouteProps {
  onlyUser: boolean;
  hasNotFinishedOAuth2?: boolean;
}

export const REDIRECT_KEY = 'redirect';

function ProtectedRoute({
  onlyUser,
  hasNotFinishedOAuth2,
}: ProtectedRouteProps) {
  const { isAuthenticated, hasFinishedOAuth2 } = useAuthContext();
  const { pathname } = useLocation();

  // Attach redirectTo search param
  const generateNavPath = useCallback(
    (path: string) => {
      const navPath: To = {
        pathname: path,
        search: `?${REDIRECT_KEY}=${pathname}`,
      };
      return navPath;
    },
    [pathname]
  );

  const passThrough = isAuthenticated === onlyUser;

  if (onlyUser && !passThrough && pathname !== PageEnum.LOGOUT) {
    return <Navigate to={generateNavPath(PageEnum.LOGIN)} />;
  }

  if (!passThrough || (hasNotFinishedOAuth2 && hasFinishedOAuth2)) {
    return <Navigate to={PageEnum.HOME} />;
  }

  return <Outlet />;
}

export default ProtectedRoute;
