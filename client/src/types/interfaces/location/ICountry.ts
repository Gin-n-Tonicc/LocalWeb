import { IObjectWithId } from '../common/IObjectWithId';

export interface ICountry extends IObjectWithId {
  name: string;
  alpha2: string;
  alpha3: string;
  phoneCode: string;
}
