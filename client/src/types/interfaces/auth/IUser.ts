import { RolesEnum } from '../../enums/RoleEnum';
import { IObjectWithId } from '../common/IObjectWithId';

export interface IUser extends IObjectWithId {
  firstname: string;
  surname: string;
  email: string;
  role: RolesEnum;
  additionalInfoRequired: boolean;
}
