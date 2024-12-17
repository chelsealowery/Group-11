package com.example.flashcardapp.flashcardappbackend.Flashcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    // Method to get all flashcards
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    // Method to add a new flashcard
    public Flashcard addFlashcard(Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }

    public Flashcard updateFlashcard(Long id, Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }

    public void deleteFlashcard(Long id) {
        flashcardRepository.deleteById(id);
    }
}

