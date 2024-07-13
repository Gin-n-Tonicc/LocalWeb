import { FieldValues, Path, PathValue, UseFormSetValue } from 'react-hook-form';
import PhoneInput1, { Country } from 'react-phone-number-input';
import { handleOptionChange } from '../../../../components/common/select-input/SelectInput';
import { IDefaultObject } from '../../../../types/interfaces/common/IDefaultObject';
import { ICountry } from '../../../../types/interfaces/location/ICountry';

export interface ICountriesReduced {
  list: Country[];
  mapAlpha2ToId: IDefaultObject<string>;
  mapIdToAlpha2: IDefaultObject<Country>;
}

interface PhoneInputProps<T extends FieldValues> {
  placeholder: string;
  currentNumber: string;
  currentCountry: string;
  countries: ICountriesReduced;
  keyToSetNumber: Path<T>;
  keyToSetCountry: Path<T>;
  setValue: UseFormSetValue<T>;
}

export const DEFAULT_COUNTRY: Country = 'BG';

export function reduceCountries(countries: ICountry[]): ICountriesReduced {
  return countries.reduce<ICountriesReduced>(
    (acc, x) => {
      return {
        list: [...acc.list, x.alpha2 as Country],
        mapAlpha2ToId: {
          ...acc.mapAlpha2ToId,
          [x.alpha2]: x.id,
        },
        mapIdToAlpha2: {
          ...acc.mapIdToAlpha2,
          [x.id]: x.alpha2 as Country,
        },
      };
    },
    { list: [], mapAlpha2ToId: {}, mapIdToAlpha2: {} }
  );
}

function PhoneInput<T extends FieldValues>(props: PhoneInputProps<T>) {
  return (
    <div className="ml-2">
      <PhoneInput1
        value={props.currentNumber}
        defaultCountry={
          props.countries.mapIdToAlpha2[props.currentCountry] || DEFAULT_COUNTRY
        }
        countryCallingCodeEditable={false}
        countries={props.countries.list}
        placeholder={props.placeholder}
        onChange={(v) =>
          handleOptionChange(
            props.keyToSetNumber,
            v as PathValue<T, Path<T>>,
            props.setValue
          )
        }
        onCountryChange={(v) =>
          handleOptionChange(
            props.keyToSetCountry,
            v as PathValue<T, Path<T>>,
            props.setValue
          )
        }
      />
    </div>
  );
}

export default PhoneInput;
