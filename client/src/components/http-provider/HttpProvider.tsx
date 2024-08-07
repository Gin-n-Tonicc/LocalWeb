import { PropsWithChildren } from 'react';
import { CachePolicies, CustomOptions, Provider, useFetch } from 'use-http';
import { authUrls } from '../../api/auth/auth';
import { baseApiUrl } from '../../api/base';
import { useAuthContext } from '../../contexts/AuthContext';
import { useToastContext } from '../../contexts/ToastContext';
import { IUser } from '../../types/interfaces/auth/IUser';
import { initialAuthUtils, isJwtExpired } from '../../utils';

// The component that is responsible for the useFetch hook options
function HttpProvider({ children }: PropsWithChildren) {
  const { isAuthenticated, user, removeJwt, removeRefresh } = useAuthContext();
  const { error } = useToastContext();

  // Prepare refresh token fetch
  const { get } = useFetch<IUser>(authUrls.refreshToken());

  const removeTokensIfExpired = () => {
    if (isJwtExpired(user.accessToken)) {
      removeJwt();
    }

    if (isJwtExpired(user.refreshToken)) {
      removeRefresh();
    }
  };

  const refreshToken = async () => {
    await get();
  };

  const options: Partial<CustomOptions> = {
    interceptors: {
      // Request interceptor (before sending the request)
      request: async ({ options, url }) => {
        const pathname = new URL(url || '').pathname;
        const isRefreshRequest = pathname.includes(authUrls.refreshTokenPath);

        removeTokensIfExpired();

        const isExpired = !Boolean(user.accessToken) && !isAuthenticated;
        const refreshTokenExpired = !Boolean(user.refreshToken);

        if (!isRefreshRequest && isExpired && !refreshTokenExpired) {
          await refreshToken();
        }

        // Include cookies
        options.credentials = 'include';
        return options;
      },
      // Response interceptor (After receiving the response, before sending it to the user)
      response: async ({ response }) => {
        if (!response.ok) {
          if (
            !initialAuthUtils.hasFinishedInitialAuth() &&
            response.status === 401
          ) {
            initialAuthUtils.finishInitialAuth();
            return response;
          }

          const data = response.data;
          const toastId = `${data.message}${data.status}${data.statusCode}`;

          const message = data.message || '';
          error(message, toastId);
        }

        return response;
      },
    },
    cachePolicy: CachePolicies.NO_CACHE,
    // default is:
    // cachePolicy: CachePolicies.CACHE_FIRST
  };

  return (
    <Provider url={baseApiUrl} options={options}>
      {children}
    </Provider>
  );
}

export default HttpProvider;
