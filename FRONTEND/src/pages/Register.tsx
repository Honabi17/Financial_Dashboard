import type React from "react";
import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";



export const Register: React.FC = () => {

    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const [error, setError] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const {register} = useAuth();

    const navigate = useNavigate();



    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setIsSubmitting(true);


        try{
            await register({
                username,
                email,
                password
            });
            navigate('/dashboard');
        }
        catch(error:any){
            if(error.response?.data?.message){
                setError(error.response.data.message);
            }
            else if(error.response?.status === 400){
                setError('Invalid data, please check the fields');
            }
            else{
                setError('An error occurred while creating the account. Please try again.');
            }
        }
        finally{
            setIsSubmitting(false);
        }
    };

    return(
       <div>
            <h2>Register</h2>
            {error && <p>{error}</p>}

            <form
                onSubmit={handleSubmit}
            >

                <div>    
                    <label 
                        htmlFor="username"
                    >
                        username
                    </label>
                    <input
                        id="username"
                        type="text"
                        value={username}
                        onChange={
                            (e) => setUsername(e.target.value)
                        }
                        required
                    />
                </div>


                <div>        
                    <label
                        htmlFor="email"
                    >
                        email
                    </label>
                    <input 
                        id="email"
                        type="email"
                        value={email}
                        onChange={
                            (e) => setEmail(e.target.value)
                        }
                        required
                    />
                </div>

                <div>
                    <label
                        htmlFor="password"
                    >
                        password
                    </label>
                    <input 
                        id="password"
                        type="password"
                        value={password}
                        onChange={
                            (e) => setPassword(e.target.value)
                        }
                        required
                    />
                </div>

                <div>
                    <button
                        type="submit"
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? 'Creating...' : 'Create'}
                    </button>

                    <button
                        type="button"
                        disabled={isSubmitting}
                        onClick={
                            () => navigate('/')
                        }
                    >
                        Cancel
                    </button>
                </div>
            </form>
            
            <p>
                Already have an account? 
                <Link to="/login">
                        Login here
                </Link>
            </p>
       </div>
    );
};