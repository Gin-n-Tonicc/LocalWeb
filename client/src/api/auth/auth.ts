import { baseApiUrl } from '../base';

export const authUrls = Object.seal({
  me: `${baseApiUrl}/auth/me`,
  register: `${baseApiUrl}/auth/register`,
  login: `${baseApiUrl}/auth/authenticate`,
  logout: `${baseApiUrl}/auth/logout`,
  completeOAuth: `${baseApiUrl}/auth/complete-oauth`,
  refreshTokenPath: '/auth/refresh-token',
  refreshToken() {
    return `${baseApiUrl}${this.refreshTokenPath}`;
  },
  forgotPassword: (email: string) =>
    `${baseApiUrl}/auth/forgot-password?email=${encodeURIComponent(email)}`,
  resetPassword: (token: string, newPassword: string) =>
    `${baseApiUrl}/auth/password-reset?token=${encodeURIComponent(
      token
    )}&newPassword=${encodeURIComponent(newPassword)}`,
});
