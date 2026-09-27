export interface Role{
    name:string;
    permissions:string[];
}

export interface User{
    username:string;
    email:string;
    roles:Role[];
}

export interface  AuthResponse{
    accessToken:string;
    refreshToken:string;
    email:string;
    username:string;
    roles:Role[];
}

export interface LoginRequest{
    email:string;
    password:string;
}

export interface RegisterRequest{
    username:string;
    email:string;
    password:string;
}