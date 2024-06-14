import { createContext, useEffect, useReducer } from "react";
import cookie from 'react-cookies';
import { authApi, endpoints } from "./APIs";

const reducer = (user, action) => {
  switch (action.type) {
    case 'login':
      return action.payload;
    case 'logout':
      return null;
    default:
      return user;
  }
};

export const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [user, dispatch] = useReducer(reducer, null);

  useEffect(() => {
    const token = cookie.load('token');
    if (token) {
      const getUser = async () => {
        try {
          let res = await authApi().get(endpoints["current-user"]);
          dispatch({ type: 'login', payload: res.data });
        } catch (error) {
          console.error("Lỗi khi lấy current user:", error);
        }
      };
      getUser();
    }
  }, []);

  return (
    <UserContext.Provider value={{ user, dispatch }}>
      {children}
    </UserContext.Provider>
  );
};