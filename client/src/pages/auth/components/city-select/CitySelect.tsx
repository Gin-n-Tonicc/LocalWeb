import { useMemo } from 'react';
import {
  FieldError,
  FieldValues,
  Path,
  PathValue,
  UseFormSetValue,
} from 'react-hook-form';
import SelectInput, {
  handleOptionChange,
  SelectOption,
} from '../../../../components/common/select-input/SelectInput';
import { ICity } from '../../../../types/interfaces/location/ICity';

interface CitySelectProps<T extends FieldValues> {
  cities: ICity[];
  error: FieldError | undefined;
  idToFindBy: string;
  keyToSetValueTo: Path<T>;
  setValue: UseFormSetValue<T>;
}

function CitySelect<T extends FieldValues>(props: CitySelectProps<T>) {
  const cities: SelectOption[] = useMemo(
    () =>
      props.cities.map((x) => ({
        value: x.id,
        label: `${x.name}, ${x.country.name}`,
      })),
    [props.cities]
  );

  return (
    <SelectInput
      defaultValue={cities.find((x) => x.value === props.idToFindBy)}
      placeholder="Select your town"
      options={cities}
      hasError={Boolean(props.error)}
      onOptionChange={(v) =>
        handleOptionChange<T>(
          props.keyToSetValueTo,
          v as PathValue<T, Path<T>>,
          props.setValue
        )
      }
    />
  );
}

export default CitySelect;
