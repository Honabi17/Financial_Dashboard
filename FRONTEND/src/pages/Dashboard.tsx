import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";


export const Dashboard: React.FC = () => {

    const {logout} = useAuth();
    
    const navigate = useNavigate();


    const handleLogout = () =>{
        logout();
        navigate('/');
    }
    return(
        <div style={{ padding: '2rem' }}>
            <h1>Dashboard Financeiro</h1>
            <button
                onClick={handleLogout}
                style={{ padding: '8px 16px', cursor: 'pointer' }}
            >
                Logout
            </button>
        </div>
    );
}