import React, { useState } from 'react';
import { db, collection, addDoc } from '../firebase';

const AddFlashcardForm = () => {
    const [question, setQuestion] = useState('');
    const [answer, setAnswer] = useState('');
    const [error, setError] = useState('');

    const handleAddFlashcard = async (e) => {
        e.preventDefault();

        try {
            const flashcardsCollection = collection(db, 'flashcards');
            await addDoc(flashcardsCollection, {
                question,
                answer
            });

            setQuestion('');
            setAnswer('');
            setError('');
            console.log("Flashcard added successfully!");
        } catch (error) {
            console.error("Error adding flashcard:", error);
            setError('Failed to add flashcard');
        }
    };

    return (
        <div>
            <h2>Add Flashcard</h2>
            {error && <p className="error">{error}</p>}
            <form onSubmit={handleAddFlashcard}>
                <input
                    type="text"
                    placeholder="Enter question"
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Enter answer"
                    value={answer}
                    onChange={(e) => setAnswer(e.target.value)}
                    required
                />
                <button type="submit">Add Flashcard</button>
            </form>
        </div>
    );
};

export default AddFlashcardForm;
