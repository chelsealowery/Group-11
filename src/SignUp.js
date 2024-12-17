import React, { useState } from 'react';
import { auth, createUserWithEmailAndPassword } from './firebase'; // Firebase methods
import { useNavigate } from 'react-router-dom'; // Use navigate to redirect
import "./SignUp.css"
const SignUp = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSignUp = async (e) => {
        e.preventDefault();
        try {
            const userCredential = await createUserWithEmailAndPassword(auth, email, password);
            console.log('Signed up user: ', userCredential.user); // Check the user data here
            navigate('/login'); // Redirect to login after successful sign up
        } catch (error) {
            setError(error.message); // Handle error during sign up
            console.error('Sign Up Error: ', error.message);
        }
    };

    return (
        <div className="signup-page"> {/* Container to center the form */}
            <div className="form-container">
                <h2>Sign Up</h2>
                {error && <p className="error">{error}</p>}
                <form onSubmit={handleSignUp}>
                    <input
                        type="email"
                        placeholder="Enter your email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        placeholder="Enter your password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit">Sign Up</button>
                </form>
                <p>
                    Already have an account?
                    <a href="/login"> Log in here</a>
                </p>
            </div>
        </div>
    );
};

export default SignUp;
