import { initializeApp } from 'firebase/app';
import { getAuth, createUserWithEmailAndPassword, signInWithEmailAndPassword, signOut } from 'firebase/auth';  // Import signOut
import { getFirestore, collection, getDocs, doc, updateDoc, deleteDoc, addDoc } from 'firebase/firestore'; // Correct modular imports

const firebaseConfig = {
    apiKey: "AIzaSyAK0T6eodrTaZsU27qoFbUENMQvcmPRLBM",
    authDomain: "flashcard-4af64.firebaseapp.com",
    projectId: "flashcard-4af64",
    storageBucket: "flashcard-4af64.appspot.com",
    messagingSenderId: "919084444743",
    appId: "1:919084444743:web:0a24f790b9bfb366fcd562",
    measurementId: "G-H440JE1B0S"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

// Initialize Firebase Authentication and Firestore Database
const auth = getAuth(app);
const db = getFirestore(app);

// Export Firebase services for use in other parts of the app
export { auth, db, createUserWithEmailAndPassword, signInWithEmailAndPassword, signOut, collection, getDocs, doc, updateDoc, deleteDoc, addDoc };
