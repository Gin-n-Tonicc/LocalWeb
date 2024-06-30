import { baseOAuthUrl } from '../base';

export const oauth2Urls = Object.seal({
  google: `${baseOAuthUrl}/google`,
  facebook: `${baseOAuthUrl}/facebook`,
});
