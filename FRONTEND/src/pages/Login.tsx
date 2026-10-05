import { useState } from "react"
import { useAuth } from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";



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
        <div>
            <h2>Login</h2>
            {error && <p>{error}</p>}

            <form
                onSubmit={handleSubmit}    
            >
                <div>
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

                <div>
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

                <div>
                    <button
                        type="submit"
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? 'Login...' : 'Login'}
                    </button>

                    <button
                        type="button"
                        disabled={isSubmitting}
                        onClick={() => navigate('/')}
                    >
                        Cancel
                    </button>
                </div>    
            </form>

            <p>
                Don't have account yet?
                <Link 
                    to="/register"
                >
                    Register here
                </Link>
            </p>
        </div>
    );
};