package edu.famu.studyflashcardapp.model;

public class UserStats {
    @DocumentId
    private String userId;
    private long totalStudyTime;
    private double accuracyRate;
    private int completedSets;

    public UserStats() {}
    public UserStats(String userId, long totalStudyTime, double accuracyRate, int completedSets)
    {
        this.userId = userId;
        this.totalStudyTime = totalStudyTime;
        this.accuracyRate = accuracyRate;
        this.completedSets = completedSets;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public long getTotalStudyTime() { return totalStudyTime; }
    public void setTotalStudyTime(long totalStudyTime) { this.totalStudyTime = totalStudyTime; }
    public double getAccuracyRate() { return accuracyRate; }
    public void setAccuracyRate(double accuracyRate) {this.accuracyRate = accuracyRate;}
    public int getCompletedSets() { return completedSets; }
    public void setCompletedSets(int completedSets) { this.completedSets = completedSets; }
}
