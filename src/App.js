import React, { useState, useEffect } from 'react';
import { Routes, Route, useNavigate } from 'react-router-dom';
import { auth } from './firebase';  // Firebase auth import
import FlashcardList from './components/FlashcardList';
import FlashcardForm from './components/FlashcardForm';
import Login from './Login';
import SignUp from './SignUp';
import './App.css';

const NotFound = () => {
    return <h2>Page Not Found</h2>;
};

const App = () => {
    const [user, setUser] = useState(null);  // Track the logged-in user state
    const navigate = useNavigate();  // Navigate to different routes

    // Track if the user is logged in or out
    useEffect(() => {
        const unsubscribe = auth.onAuthStateChanged((currentUser) => {
            setUser(currentUser);  // Set the user state when authenticated
        });
        return () => unsubscribe();  // Cleanup the subscription
    }, []);

    const handleLogout = () => {
        auth.signOut().then(() => {
            setUser(null);  // Clear user state on logout
            navigate('/login');  // Redirect to login page
        });
    };

    const handleSignUp = () => {
        navigate('/login'); // Direct to login after successful sign up
    };

    return (
        <div className="page-container">
            {/* Show logout button if user is logged in */}
            {user && (
                <button className="logout-button" onClick={handleLogout}>
                    Logout
                </button>
            )}

            <Routes>
                {/* Login Route */}
                <Route path="/" element={<Login onLogin={setUser} />} />
                <Route path="/login" element={<Login onLogin={setUser} />} />

                {/* Sign Up Route */}
                <Route path="/signup" element={<SignUp onSignUp={handleSignUp} />} />

                {/* Routes for Flashcards page, only accessible if user is logged in */}
                {user && (
                    <>
                        <Route path="/flashcards" element={<FlashcardList />} />
                        <Route path="/add-flashcard" element={<FlashcardForm />} />
                    </>
                )}

                {/* Catch-all for invalid routes */}
                <Route path="*" element={<NotFound />} />
            </Routes>
        </div>
    );
};

export default App;
