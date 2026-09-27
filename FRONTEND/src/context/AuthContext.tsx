import { createContext, useContext,useEffect, useState } from "react";
import type { AuthResponse, LoginRequest, RegisterRequest, User } from "../types/auth";
import { api } from "../api/axios";



interface AuthContextType{
    user:User|null;
    loading:boolean;
    register:(data:RegisterRequest) => Promise<void>;
    login:(data:LoginRequest) => Promise<void>;
    logout:() => void;
}


const AuthContext = createContext<AuthContextType>({} as AuthContextType);

export const AuthProvider: React.FC<{children:React.ReactNode}> = ({children}) => {
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState<boolean>(true);


    useEffect(() => {
        const fetchUser = async() => {
            const token = localStorage.getItem('accessToken');
            if(token){
                try{
                    const response = await api.get<User>('/auth/me');
                    setUser(response.data);
                }
                catch{
                    localStorage.removeItem('accessToken');
                    localStorage.removeItem('refreshToken');
                }
            }
            setLoading(false);
        };
        fetchUser();
    },[]);


    const login = async(data:LoginRequest) => {
        const response = await api.post<AuthResponse>('/auth/login', data);
        const{
            accessToken,
            refreshToken, 
            email, 
            username, 
            roles
        } = response.data;

        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
        setUser({email, username, roles});
    };


    const register = async(data:RegisterRequest) => {
        const response = await api.post<AuthResponse>('/auth/register', data);
        const{
            accessToken,
            refreshToken,
            email,
            username,
            roles
        } = response.data;

        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
        setUser({email, username, roles});
    };


    const logout = () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        setUser(null);
    };


    return(
        <AuthContext.Provider value={{
            user,
            loading,
            login,
            register,
            logout
        }}>
            {children}
        </AuthContext.Provider>
    );
};