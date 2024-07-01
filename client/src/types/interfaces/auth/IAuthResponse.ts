import { IAuthRefreshResponse } from './iAuthRefreshResponse';
import { IUser } from './IUser';

export interface IAuthResponse extends IAuthRefreshResponse {
  user: IUser;
}
