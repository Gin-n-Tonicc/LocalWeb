import { createContext, PropsWithChildren, useContext } from 'react';
import { Bounce, toast, ToastContainer, ToastOptions } from 'react-toastify';
import { ToastEnum } from '../types/enums/ToastEnum';

type AddToastFunc = (content: string, id?: string) => void;

type ToastContextType = {
  success: AddToastFunc;
  error: AddToastFunc;
  info: AddToastFunc;
};

const ToastContext = createContext<ToastContextType | null>(null);

const TOAST_OPTIONS: ToastOptions<unknown> = {
  position: 'top-right',
  transition: Bounce,
};

const toastTypeToFuncMap = {
  [ToastEnum.SUCCESS]: toast.success,
  [ToastEnum.ERROR]: toast.error,
  [ToastEnum.INFO]: toast.info,
};

export function ToastProvider({ children }: PropsWithChildren) {
  const addToast = (type: ToastEnum, content: string, id?: string) => {
    toastTypeToFuncMap[type].call(null, content, {
      ...TOAST_OPTIONS,
      toastId: id,
    });
  };

  const success = addToast.bind(null, ToastEnum.SUCCESS);
  const error = addToast.bind(null, ToastEnum.ERROR);
  const info = addToast.bind(null, ToastEnum.INFO);

  return (
    <ToastContext.Provider
      value={{
        success,
        error,
        info,
      }}>
      <ToastContainer
        limit={3}
        position="top-right"
        autoClose={3000}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="dark"
        transition={Bounce}
      />
      {children}
    </ToastContext.Provider>
  );
}

export const useToastContext = () => {
  const toastContext = useContext(ToastContext);

  if (!toastContext) {
    throw new Error(
      'useToastContext has to be used within <ToastContext.Provider>'
    );
  }

  return toastContext;
};
