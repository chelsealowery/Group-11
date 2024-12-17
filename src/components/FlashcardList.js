import React, { useState, useEffect } from 'react';
import './FlashcardList.css';

const FlashcardList = () => {
    const [flashcards, setFlashcards] = useState([]);
    const [currentIndex, setCurrentIndex] = useState(0);
    const [isEditing, setIsEditing] = useState(false);
    const [editedQuestion, setEditedQuestion] = useState('');
    const [editedAnswer, setEditedAnswer] = useState('');
    const [editCardId, setEditCardId] = useState(null);
    const [isFlipping, setIsFlipping] = useState(false);
    const [progress, setProgress] = useState(0);
    const [isFlipped, setIsFlipped] = useState(false); // Ensures card flip works
    const [searchQuery, setSearchQuery] = useState('');
    const [newQuestion, setNewQuestion] = useState('');
    const [newAnswer, setNewAnswer] = useState('');

    // Fetch flashcards from the backend
    const fetchFlashcards = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/flashcards');
            const data = await response.json();
            setFlashcards(data);
        } catch (error) {
            console.error('Error fetching flashcards:', error);
        }
    };

    useEffect(() => {
        fetchFlashcards();
    }, []);

    const handlePrevious = () => {
        setCurrentIndex((prevIndex) => (prevIndex === 0 ? flashcards.length - 1 : prevIndex - 1));
    };

    const handleNext = () => {
        setCurrentIndex((prevIndex) => (prevIndex === flashcards.length - 1 ? 0 : prevIndex + 1));
    };

    // Edit the current flashcard
    const handleEdit = (flashcard) => {
        setEditedQuestion(flashcard.question);
        setEditedAnswer(flashcard.answer);
        setIsEditing(true);
        setEditCardId(flashcard.id);
    };

    // Save the changes to a flashcard
    const handleSave = async () => {
        const response = await fetch(`http://localhost:8080/api/flashcards/${editCardId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ question: editedQuestion, answer: editedAnswer }),
        });

        if (response.ok) {
            fetchFlashcards();
            setIsEditing(false);
            setEditedQuestion('');
            setEditedAnswer('');
            setEditCardId(null);
        } else {
            console.error('Failed to update flashcard');
        }
    };

    const handleCancel = () => {
        setIsEditing(false);
        setEditedQuestion('');
        setEditedAnswer('');
        setEditCardId(null);
    };

    // Delete a flashcard
    const handleDelete = async (flashcardId) => {
        const response = await fetch(`http://localhost:8080/api/flashcards/${flashcardId}`, {
            method: 'DELETE',
        });

        if (response.ok) {
            fetchFlashcards();
        } else {
            console.error('Failed to delete flashcard');
        }
    };

    // Add a new flashcard
    const handleAddFlashcard = async () => {
        if (newQuestion && newAnswer) {
            const response = await fetch('http://localhost:8080/api/flashcards', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ question: newQuestion, answer: newAnswer }),
            });

            if (response.ok) {
                fetchFlashcards();
                setNewQuestion('');
                setNewAnswer('');
            } else {
                console.error('Failed to add flashcard');
            }
        }
    };

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    };

    const handleFetchDefinition = async () => {
        const response = await fetch(`https://api.dictionaryapi.dev/api/v2/entries/en/${newQuestion}`);
        const data = await response.json();
        if (data[0]?.meanings[0]?.definitions[0]?.definition) {
            setNewAnswer(data[0].meanings[0].definitions[0].definition);
        } else {
            setNewAnswer("Definition not found.");
        }
    };

    const filteredFlashcards = flashcards.filter((flashcard) =>
        flashcard.question.toLowerCase().includes(searchQuery.toLowerCase()) ||
        flashcard.answer.toLowerCase().includes(searchQuery.toLowerCase())
    );

    // Handle flipping of the card
    const handleFlip = () => {
        setIsFlipping(true);
        setIsFlipped((prev) => !prev);
        setProgress(0);
        setTimeout(() => {
            setIsFlipping(false);
        }, 3000);
    };

    return (
        <div className="flashcard-list-container">
            <h2>Flashcards</h2>

            <div className="search-container">
                <input
                    type="text"
                    placeholder="Search flashcards..."
                    value={searchQuery}
                    onChange={handleSearchChange}
                    className="search-input"
                />
            </div>

            <div className="add-flashcard-form">
                <input
                    type="text"
                    placeholder="Enter question"
                    value={newQuestion}
                    onChange={(e) => setNewQuestion(e.target.value)}
                    className="add-flashcard-input"
                />
                <textarea
                    placeholder="Enter answer"
                    value={newAnswer}
                    onChange={(e) => setNewAnswer(e.target.value)}
                    className="add-flashcard-input"
                />
                <button onClick={handleFetchDefinition} className="fetch-definition-btn">Fetch Definition</button>
                <button onClick={handleAddFlashcard} className="add-flashcard-btn">Add Flashcard</button>
            </div>

            {filteredFlashcards.length > 0 ? (
                <div className="flashcard" onClick={handleFlip}>
                    <div className={`flashcard-inner ${isFlipped ? 'flipped' : ''}`}>
                        <div className="flashcard-content">
                            <p><strong>Question:</strong> {filteredFlashcards[currentIndex]?.question}</p>
                        </div>
                        <div className="flashcard-back">
                            <p><strong>Answer:</strong> {filteredFlashcards[currentIndex]?.answer}</p>
                        </div>
                    </div>
                </div>
            ) : (
                <p>Card not found</p>
            )}

            <div className="progress-bar-container">
                <div className="progress-bar" style={{ width: `${progress}%` }} />
            </div>

            {isEditing && editCardId === filteredFlashcards[currentIndex]?.id ? (
                <div className="edit-form">
                    <input
                        type="text"
                        value={editedQuestion}
                        onChange={(e) => setEditedQuestion(e.target.value)}
                        className="edit-input"
                    />
                    <textarea
                        value={editedAnswer}
                        onChange={(e) => setEditedAnswer(e.target.value)}
                        className="edit-input"
                    />
                    <button onClick={handleSave} className="save-btn">Save</button>
                    <button onClick={handleCancel} className="cancel-btn">Cancel</button>
                </div>
            ) : (
                <div className="action-buttons">
                    <button onClick={() => handleEdit(filteredFlashcards[currentIndex])} className="edit-btn">Edit</button>
                    <button
                        onClick={() => handleDelete(filteredFlashcards[currentIndex]?.id)}
                        className="delete-btn"
                    >
                        Delete
                    </button>
                </div>
            )}

            <div className="flashcard-navigation">
                <button onClick={handlePrevious} className="nav-btn">Previous</button>
                <button onClick={handleNext} className="nav-btn">Next</button>
            </div>
        </div>
    );
};

export default FlashcardList;
