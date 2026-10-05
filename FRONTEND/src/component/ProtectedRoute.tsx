import { Navigate, Outlet } from 'react-router-dom';
import {useAuth} from '../context/AuthContext';


export const ProtectedRoute = () => {
    
    const{isAuthenticated, isLoading} = useAuth();


    if(isLoading){
        return(
            < div 
                style={{
                    display:'flex',
                    justifyContent:'center',
                    alignItems:'center',
                    height:'100vh'
                }}
            >
                <p>
                    Load session...
                </p>
            </div>
        );
    }
    return isAuthenticated ? <Outlet /> : <Navigate  to="/login" replace />
}