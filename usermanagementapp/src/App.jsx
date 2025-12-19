import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import Register from './pages/Register';
import Login from './pages/Login';
import Profile from './pages/Profile';

function App() {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        checkAuth();
    }, []);

    const checkAuth = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/users/profile', {
                credentials: 'include'
            });
            if (res.ok) {
                const data = await res.json();
                setUser(data.data);
            }
        } catch (err) {
            console.log('Not authenticated');
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <div className="text-lg">Loading...</div>
            </div>
        );
    }

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/register" element={
                    user ? <Navigate to="/profile" /> : <Register setUser={setUser} />
                } />
                <Route path="/login" element={
                    user ? <Navigate to="/profile" /> : <Login setUser={setUser} />
                } />
                <Route path="/profile" element={
                    user ? <Profile user={user} setUser={setUser} /> : <Navigate to="/login" />
                } />
                <Route path="/" element={<Navigate to={user ? "/profile" : "/login"} />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;