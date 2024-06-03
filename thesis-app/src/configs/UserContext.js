import { createContext, useEffect, useReducer } from "react";
import cookie from 'react-cookies'
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
    const storedUser = cookie.load('user');
    if (storedUser) {
      dispatch({ type: 'login', payload: storedUser });
    } else {
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
    }
  }, []);

  useEffect(() => {
    if (user) {
      cookie.save('user', user);
    }
  }, [user]);

  return (
    <UserContext.Provider value={{ user, dispatch }}>
      {children}
    </UserContext.Provider>
  );
}