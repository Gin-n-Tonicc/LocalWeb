import { useEffect } from 'react';
import { useFetch } from 'use-http';
import { authUrls } from '../api/auth/auth';
import { useAuthContext } from '../contexts/AuthContext';
import { IAuthResponse } from '../types/interfaces/auth/IAuthResponse';

// The hook that authenticates our user
export default function useAuthenticate(shouldLogoutUser: boolean = true) {
  const { user, loginUser, logoutUser } = useAuthContext();

  const { get, response, loading } = useFetch<IAuthResponse>(authUrls.me);

  // Authenticate user on mount
  useEffect(() => {
    async function fetchApi() {
      const data = await get();

      if (response.ok) {
        loginUser(data);
      } else if (shouldLogoutUser) {
        logoutUser();
      }
    }

    fetchApi();
  }, []);

  return { user, loading };
}
