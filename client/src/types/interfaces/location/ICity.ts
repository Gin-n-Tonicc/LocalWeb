import { IObjectWithId } from '../common/IObjectWithId';
import { ICountry } from './ICountry';

export interface ICity extends IObjectWithId {
  country: ICountry;
  name: string;
  slug: string;
}
