package edu.famu.studyflashcardapp.controller;

import edu.famu.studyflashcardapp.model.UserStats;
import edu.famu.studyflashcardapp.service.UserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-stats")
public class UserStatsController {
    @Autowired
    private UserStatsService userStatsService;

    @GetMapping("/{userId}")
    public UserStats getUserStats(@PathVariable String userId) {
        return userStatsService.getUserStats(userId);
    }

    @PostMapping("/")
    public void updateUserStats(@RequestBody UserStats stats) {
        userStatsService.updateUserStats(stats);
    }
}
