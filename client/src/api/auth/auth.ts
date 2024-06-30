import { baseApiUrl } from '../base';

export const authUrls = Object.seal({
  me: `${baseApiUrl}/auth/me`,
  register: `${baseApiUrl}/auth/register`,
  login: `${baseApiUrl}/auth/authenticate`,
  logout: `${baseApiUrl}/auth/logout`,
  refreshTokenPath: '/auth/refresh-token',
  refreshToken() {
    return `${baseApiUrl}${this.refreshTokenPath}`;
  },
});
