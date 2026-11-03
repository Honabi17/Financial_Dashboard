import type React from "react";
import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import "./auth.css";



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
       <div className="auth-page">
            <div className="auth-card">

                <h2>Register</h2>
                <p className="auth-subtitle">
                    Create your account to get started.
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
                            htmlFor="username"
                        >
                            Username
                        </label>
                        <input
                            id="username"
                            type="text"
                            value={username}
                            onChange={
                                (e) => setUsername(e.target.value)
                            }
                            autoComplete="username"
                            required
                        />
                    </div>


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
                            onChange={
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
                            minLength={6}
                            autoComplete="new-password"
                            required
                        />
                    </div>

                    <div className="auth-actions">
                        <button
                            type="submit"
                            className="btn-primary"
                            disabled={isSubmitting}
                        >
                            {isSubmitting ? 'Creating...' : 'Create'}
                        </button>

                        <button
                            type="button"
                            className="btn-secondary"
                            disabled={isSubmitting}
                            onClick={
                                () => navigate('/')
                            }
                        >
                            Cancel
                        </button>
                    </div>
                </form>
                
                <p className="auth-footer">
                    Already have an account? 
                    <Link to="/login">
                            Login here
                    </Link>
                </p>
            </div>
       </div>
    );
};