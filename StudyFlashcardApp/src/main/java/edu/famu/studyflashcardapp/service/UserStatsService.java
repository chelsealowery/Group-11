package edu.famu.studyflashcardapp.service;

import edu.famu.studyflashcardapp.model.UserStats;
import edu.famu.studyflashcardapp.repository.UserStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserStatsService
{
    @Autowired
    private UserStatsRepository userStatsRepository;

    public UserStats getUserStats(String userId)
    {
        return userStatsRepository.findById(userId).orElse(new UserStats(userId,0,0.0,0));
    }

    public void updateUserStats(UserStats stats){
        userStatsRepository.save(stats);
    }
}
