import { Route, Routes } from 'react-router-dom';
import 'react-toastify/dist/ReactToastify.css';
import Authenticate from './components/authenticate/Authenticate';
import ProtectedRoute from './components/common/protected-route/ProtectedRoute';
import HttpProvider from './components/http-provider/HttpProvider';
import { AuthProvider } from './contexts/AuthContext';
import { LocationProvider } from './contexts/LocationContext';
import { ToastProvider } from './contexts/ToastContext';
import FinishRegister from './pages/auth/finish-register/FinishRegister';
import ForgotPassword from './pages/auth/forgot-password/ForgotPassword';
import Logout from './pages/auth/logout/Logout';
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
                  {/* Only guests */}
                  <Route element={<ProtectedRoute onlyUser={false} />}>
                    <Route path={PageEnum.LOGIN} element={<MainAuth />} />
                    <Route path={PageEnum.REGISTER} element={<MainAuth />} />
                    <Route
                      path={PageEnum.FORGOT_PASSWORD}
                      element={<ForgotPassword />}
                    />
                  </Route>

                  {/* Only logged users BUT **NOT** FINISHED OAUTH2 */}
                  <Route
                    element={
                      <ProtectedRoute
                        onlyUser={true}
                        hasNotFinishedOAuth2={true}
                      />
                    }>
                    <Route
                      path={PageEnum.FINISH_REGISTER}
                      element={<FinishRegister />}
                    />
                  </Route>

                  {/* Only logged users BUT **WITH** FINISHED OAUTH2 */}
                  <Route element={<ProtectedRoute onlyUser={true} />}>
                    <Route path={PageEnum.LOGOUT} element={<Logout />} />
                  </Route>
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
