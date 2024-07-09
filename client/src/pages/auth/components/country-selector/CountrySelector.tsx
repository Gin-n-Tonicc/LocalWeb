import { useMemo, useState } from 'react';
import countryList from 'react-select-country-list';
import SelectInput from '../../../../components/common/select-input/SelectInput';

export interface SelectOption {
  readonly value: string;
  readonly label: string;
}

function CountrySelector() {
  const [value, setValue] = useState('');
  const options = useMemo(() => countryList().getData(), []);
  console.log(options);

  const changeHandler = (value: any) => {
    setValue(value);
  };

  return <SelectInput options={options} onOptionChange={changeHandler} />;
}

export default CountrySelector;
