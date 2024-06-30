import { Route, Routes } from 'react-router-dom';
import './App.css';
import Auth from './pages/auth/Auth';
import { PageEnum } from './types/enums/PageEnum';

function App() {
  return (
    <>
      <Routes>
        <Route path={PageEnum.LOGIN} element={<Auth />} />
        <Route path={PageEnum.REGISTER} element={<Auth />} />
      </Routes>
    </>
  );
}

export default App;
