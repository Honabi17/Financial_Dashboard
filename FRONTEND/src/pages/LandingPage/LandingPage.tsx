import { Link, Navigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext"
import "./LandingPage.css";



export const LandingPage: React.FC = () => {

    const {isAuthenticated, isLoading} = useAuth();

    

    if(isLoading){
        return(
            <div>
                Loading...
            </div>
        );
    }

    if(isAuthenticated){
        return <Navigate to="/dashboard" replace/>;
    }


    return(
        <div className="landing-page">

            <header className="hero">

                <div className="hero-content">

                    <span className="hero-badge">
                        Personal Finance Management
                    </span>

                    <h1>
                        Take Control of Your 
                        <span> Finances</span>
                    </h1>

                    <p className="text-secondary">
                        Track income, manage expenses, monitor your balance
                        and gain valuable insights into your financial life
                        through an intuitive dashboard.
                    </p>

                    <div className="hero-actions">

                        <Link
                            className="btn-primary"
                            to="/register"
                        >
                            Sing up
                        </Link>
                        
                        <Link
                            className="btn-secondary"
                            to="/login"
                        >
                            Login
                        </Link>

                    </div>
                </div>

                <div className="hero-preview">

                    <div className="preview-card">

                        <div className="preview-kpis">

                            <div className="card">
                                <h4>Income</h4>
                                <span>4250€</span>
                            </div>

                            <div className="card">
                                <h4>Expense</h4>
                                <span>2180€</span>
                            </div>

                            <div className="card">
                                <h4>Balance</h4>
                                <span>2070€</span>
                            </div>
                        
                        </div>

                        <div className="chart-placeholder">

                            <div className="chart-header">

                                Monthly Performance
                            
                            </div>

                            <div className="chart-bars">

                                <div className="bar h-40"></div>
                                <div className="bar h-60"></div>
                                <div className="bar h-80"></div>
                                <div className="bar h-50"></div>
                                <div className="bar h-70"></div>
                                <div className="bar h-90"></div>

                            </div>

                        </div>

                    </div>

                </div>

            </header>

            <section className="features">

                <div className="card feature-card">

                    <h3>Track income</h3>
                    <p>Record all your sources of income in one place.</p>

                </div>

                <div className="card feature-card">

                    <h3>Manage Expenses</h3>
                    <p>Categorize spending and analyze your habits.</p>
                </div>

                <div className="card feature-card">

                    <h3>Financial Reports</h3>
                    <p>Understand your finances through visual reports.</p>
                </div>

            </section>

            <footer className="landing-footer">

                <p>Financial Dashboard © 2026</p>

            </footer>

        </div>
    );
};