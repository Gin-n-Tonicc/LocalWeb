import { RolesEnum } from '../../enums/RoleEnum';
import { IObjectWithId } from '../common/IObjectWithId';

export interface IUser extends IObjectWithId {
  name: string;
  surname: string;
  email: string;
  role: RolesEnum;
  additionalInfoRequired: boolean;
}
