import React, { createContext, useContext,useEffect, useState } from "react";
import type { AuthResponse, LoginRequest, RegisterRequest, User } from "../types/auth";
import { api } from "../api/axios";



interface AuthContextType{
    user:User|null;
    isLoading:boolean;
    isAuthenticated:boolean;
    register:(data:RegisterRequest) => Promise<void>;
    login:(data:LoginRequest) => Promise<void>;
    logout:() => void;
}


const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {

    const context = useContext(AuthContext);
    if(!context){
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
}

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
                    setUser(null);
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
        setUser({email, username, roles} as User);
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
        setUser({email, username, roles} as User);
    };


    const logout = () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        setUser(null);
    };


    return(
        <AuthContext.Provider value={{
            user,
            isLoading: loading,
            isAuthenticated: !!user,
            login,
            register,
            logout
        }}>
            {children}
        </AuthContext.Provider>
    );
};