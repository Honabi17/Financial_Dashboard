import { useState } from "react"
import { useAuth } from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import "./auth.css";



export const Login: React.FC = () =>{

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const [error, setError] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const{login} = useAuth();

    const navigate = useNavigate();



    const handleSubmit = async (e: React.FormEvent) => {

        e.preventDefault();
        setError(null);
        setIsSubmitting(true);


        try{
            await login ({
                email,
                password
            });
            navigate('/dashboard');
        }
        catch(error:any){
            if(error.response?.data?.message){
                setError(error.response.data.message);
            }
            else{
                setError('Wrong credentials. Please try again.');
            }
            
        }
        finally{
            setIsSubmitting(false);
        }
    };

    return(
        <div className="auth-page">
            <div className="auth-card">

                <h2>Login</h2>
                <p className="auth-subtitle">
                    Welcome back. Sign in to continue.
                </p>

                {error && 
                    <p className="auth-error">
                        {error}
                    </p>
                }

                <form className="auth-form"
                    onSubmit={handleSubmit}    
                >
                    <div className="auth-field">
                        <label 
                            htmlFor="email"
                        > 
                            Email
                        </label>
                        <input
                            id="email"
                            type="email"
                            value={email}
                            onChange = {
                                (e) => setEmail(e.target.value)
                            }
                            autoComplete="email"
                            required
                        />
                    </div>

                    <div className="auth-field">
                        <label 
                            htmlFor="password"
                        >
                            Password
                        </label>
                        <input
                            id="password"
                            type="password"
                            value={password}
                            onChange={
                                (e) => setPassword(e.target.value)
                            }
                            autoComplete="current-password"
                            required
                        />
                    </div>

                    <div className="auth-actions">
                        <button
                            type="submit"
                            className="btn-primary"
                            disabled={isSubmitting}
                        >
                            {isSubmitting ? 'Login...' : 'Login'}
                        </button>

                        <button
                            type="button"
                            className="btn-secondary"
                            disabled={isSubmitting}
                            onClick={() => navigate('/')}
                        >
                            Cancel
                        </button>
                    </div>    
                </form>

                <p className="auth-footer">
                    Don't have account yet?
                    <Link 
                        to="/register"
                    >
                        Register here
                    </Link>
                </p>
            </div>
        </div>
    );
};