package com.example.flashcardapp.flashcardappbackend.Flashcard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    // Additional query methods can be defined here if needed
}
