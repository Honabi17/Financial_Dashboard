import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import './App.css'
import { AuthProvider } from './context/AuthContext';
import { ProtectedRoute } from './component/ProtectedRoute';
import { Register } from './pages/Register';
import { Login } from './pages/Login';
import { LandingPage } from './pages/LandingPage/LandingPage';
import { Dashboard } from './pages/Dashboard';

function App() {
  

  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>

          <Route 
            path='/'
            element={<LandingPage/>}
          />

          <Route 
            path='/login' 
            element={<Login />} 
          />

          <Route 
            path='/register'
            element={<Register/>}
          />


          <Route 
            element={<ProtectedRoute/>}
          >
            <Route
              path='/dashboard'
              element={<Dashboard/>}
            />
          </Route>

          <Route
            path='*'
            element={<Navigate to="/" replace />}
          />
          
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App
