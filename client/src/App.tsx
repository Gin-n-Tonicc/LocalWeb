import { Route, Routes } from 'react-router-dom';
import 'react-toastify/dist/ReactToastify.css';
import Authenticate from './components/authenticate/Authenticate';
import HttpProvider from './components/http-provider/HttpProvider';
import { AuthProvider } from './contexts/AuthContext';
import { ToastProvider } from './contexts/ToastContext';
import ForgotPassword from './pages/auth/forgot-password/ForgotPassword';
import MainAuth from './pages/auth/main-auth/MainAuth';
import { PageEnum } from './types/enums/PageEnum';

function App() {
  return (
    <div>
      <ToastProvider>
        <AuthProvider>
          <HttpProvider>
            <Authenticate>
              <Routes>
                <Route path={PageEnum.LOGIN} element={<MainAuth />} />
                <Route path={PageEnum.REGISTER} element={<MainAuth />} />
                <Route
                  path={PageEnum.FORGOT_PASSWORD}
                  element={<ForgotPassword />}
                />
              </Routes>
            </Authenticate>
          </HttpProvider>
        </AuthProvider>
      </ToastProvider>
    </div>
  );
}

export default App;
