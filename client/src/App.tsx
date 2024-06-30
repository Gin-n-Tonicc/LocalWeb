import { Route, Routes } from 'react-router-dom';
import Authenticate from './components/authenticate/Authenticate';
import HttpProvider from './components/http-provider/HttpProvider';
import { AuthProvider } from './contexts/AuthContext';
import Auth from './pages/auth/Auth';
import { PageEnum } from './types/enums/PageEnum';

function App() {
  return (
    <>
      <AuthProvider>
        <HttpProvider>
          <Authenticate>
            <Routes>
              <Route path={PageEnum.LOGIN} element={<Auth />} />
              <Route path={PageEnum.REGISTER} element={<Auth />} />
            </Routes>
          </Authenticate>
        </HttpProvider>
      </AuthProvider>
    </>
  );
}

export default App;
