import { SubmitHandler, useForm } from 'react-hook-form';
import FormInput from '../../../../components/common/form-input/FormInput';
import rocketImage from '../../img/rocket.png';
import {
  FIRST_NAME_VALIDATIONS,
  SUR_NAME_VALIDATIONS,
} from '../../validations-common';

type FinishRegisterFormTypes = {
  name: string;
  surname: string;
};

function FinishRegisterForm() {
  const { handleSubmit, control, watch } = useForm<FinishRegisterFormTypes>({
    defaultValues: {
      name: '',
      surname: '',
    },
    mode: 'onSubmit',
  });

  const onSubmit: SubmitHandler<FinishRegisterFormTypes> = (v) => {};

  return (
    <div className="form-box login-register-form-element login-form">
      <img
        className="form-box-decoration overflowing"
        src={rocketImage}
        alt="rocket"
      />
      <h2 className="form-box-title">Finish Register</h2>
      <form className="form" onSubmit={handleSubmit(onSubmit)}>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="First Name"
              id="register-first-name"
              name="name"
              rules={FIRST_NAME_VALIDATIONS}
            />
          </div>
        </div>
        <div className="form-row">
          <div className="form-item">
            <FormInput
              control={control}
              type="text"
              placeholder="Sur Name"
              id="register-sur-name"
              name="surname"
              rules={SUR_NAME_VALIDATIONS}
            />
          </div>
        </div>
        <div className="form-row"></div>
        <div className="form-row">
          <div className="form-item">
            <button className="button medium secondary">Register</button>
          </div>
        </div>
      </form>
    </div>
  );
}

export default FinishRegisterForm;
