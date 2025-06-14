package com.studytracker;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class StudySession {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String subject;
    private String notes;
    private int difficulty; // 1-5 scale
    
    // Constructor
    public StudySession(LocalDateTime startTime, LocalDateTime endTime, String subject) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.notes = "";
        this.difficulty = 3; // default medium
    }
    
    // TODO: Implement getters and setters for all fields
    // Remember to validate that endTime is after startTime in the setters!
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        // TODO: Add validation - if endTime is set, make sure startTime is before it
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        // TODO: Add validation - make sure endTime is after startTime
        this.endTime = endTime;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        // TODO: Add validation - subject shouldn't be null or empty
        this.subject = subject;
    }
    
    // TODO: Add getter and setter for notes
    
    // TODO: Add getter and setter for difficulty (validate it's between 1-5)
    
    // TODO: Implement getDurationInMinutes() method
    // Hint: Use Duration.between(startTime, endTime).toMinutes()
    public int getDurationInMinutes() {
        // YOUR CODE HERE
        return 0;
    }
    
    // TODO: Implement toString() method that returns a nice string representation
    // Example format: "Math study session on 2024-01-15 for 45 minutes"
    // Hint: Use DateTimeFormatter.ofPattern("yyyy-MM-dd") for the date
    @Override
    public String toString() {
        // YOUR CODE HERE
        return "";
    }
}