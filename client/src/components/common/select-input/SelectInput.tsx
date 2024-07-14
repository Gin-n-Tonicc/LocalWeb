import { FieldValues, Path, PathValue, UseFormSetValue } from 'react-hook-form';
import Select, {
  ControlProps,
  CSSObjectWithLabel,
  GroupBase,
  SingleValue,
} from 'react-select';
import './SelectInput.scss';

export interface SelectOption {
  readonly value: string;
  readonly label: string;
}

interface SelectInputProps {
  options: SelectOption[];
  onOptionChange: (v: string | undefined) => void;
  hasError?: boolean;
  placeholder?: string;
  defaultValue?: SingleValue<SelectOption>;
}

export function handleOptionChange<T extends FieldValues>(
  key: Path<T>,
  vFromInput: PathValue<T, Path<T>> | undefined,
  setValue: UseFormSetValue<T>
): void {
  const v = vFromInput?.toString() || '';

  setValue(key, v, {
    shouldValidate: true,
    shouldDirty: true,
    shouldTouch: true,
  });
}

// The component that displays a dropdown with values based on the props
function SelectInput(props: SelectInputProps) {
  const getStyles = (
    baseStyles: CSSObjectWithLabel,
    state: ControlProps<SelectOption, false, GroupBase<SelectOption>>
  ): CSSObjectWithLabel => {
    if (!props.hasError) {
      return baseStyles;
    }

    return {
      ...baseStyles,
      borderColor: '#f9515c',
    };
  };

  return (
    <>
      <Select
        defaultValue={props.defaultValue}
        name="category-select"
        placeholder={props.placeholder}
        options={props.options}
        onChange={(newValue) => {
          props.onOptionChange(newValue?.value);
        }}
        styles={{
          control: getStyles,
        }}
        theme={(theme) => ({
          ...theme,
          colors: {
            ...theme.colors,
            primary: 'rgba(102, 16, 242, 1)',
            primary25: 'rgba(102, 16, 242, 0.25)',
            primary50: 'rgba(102, 16, 242, 0.50)',
            primary75: 'rgba(102, 16, 242, 0.75)',
          },
        })}
        classNames={{
          control: (state) => 'select-input',
        }}
      />
    </>
  );
}

export default SelectInput;
