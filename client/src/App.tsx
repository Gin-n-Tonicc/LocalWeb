import { Route, Routes } from 'react-router-dom';
import 'react-toastify/dist/ReactToastify.css';
import Authenticate from './components/authenticate/Authenticate';
import HttpProvider from './components/http-provider/HttpProvider';
import { AuthProvider } from './contexts/AuthContext';
import { LocationProvider } from './contexts/LocationContext';
import { ToastProvider } from './contexts/ToastContext';
import FinishRegister from './pages/auth/finish-register/FinishRegister';
import ForgotPassword from './pages/auth/forgot-password/ForgotPassword';
import MainAuth from './pages/auth/main-auth/MainAuth';
import { PageEnum } from './types/enums/PageEnum';

function App() {
  return (
    <div>
      <ToastProvider>
        <AuthProvider>
          <HttpProvider>
            <LocationProvider>
              <Authenticate>
                <Routes>
                  <Route path={PageEnum.LOGIN} element={<MainAuth />} />
                  <Route path={PageEnum.REGISTER} element={<MainAuth />} />
                  <Route
                    path={PageEnum.FORGOT_PASSWORD}
                    element={<ForgotPassword />}
                  />
                  <Route
                    path={PageEnum.FINISH_REGISTER}
                    element={<FinishRegister />}
                  />
                </Routes>
              </Authenticate>
            </LocationProvider>
          </HttpProvider>
        </AuthProvider>
      </ToastProvider>
    </div>
  );
}

export default App;
