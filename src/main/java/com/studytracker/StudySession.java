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

        if (this.endTime != null && startTime != null && startTime.isAfter(this.endTime)) {
            System.err.println("Start time must be before end time");
            return;
        }
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        // TODO: Add validation - make sure endTime is after startTime

        if (this.startTime != null && endTime != null && endTime.isBefore(this.startTime)) {
            System.err.println("You need to start a session first in order to end it.");
            return;
        }
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        // TODO: Add validation - subject shouldn't be null or empty
        if (subject != null && subject.isEmpty()) {
            System.err.println("Subject cannot be null or empty");
            return;
        }
        this.subject = subject;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        if (notes != null && notes.isEmpty()) {
            System.err.println("Stop cheating, we all know you didn't take notes.");
            return;
        }
        this.notes = notes;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        if (difficulty < 1 || difficulty > 5) {
            System.err.println("Difficulty must be between 1 and 5.");
            return;
        }
        this.difficulty = difficulty;
    }

    // TODO: Add getter and setter for difficulty (validate it's between 1-5)

    // TODO: Implement getDurationInMinutes() method
    // Hint: Use Duration.between(startTime, endTime).toMinutes()
    public int getDurationInMinutes() {
        if (getStartTime() == null || getEndTime() == null) {
            System.err.println("Invalid start or end time.");
        }
        return (int) Duration.between(startTime, endTime).toMinutes();
    }

    // TODO: Implement toString() method that returns a nice string representation
    // Example format: "Math study session on 2024-01-15 for 45 minutes"
    // Hint: Use DateTimeFormatter.ofPattern("yyyy-MM-dd") for the date
    @Override
    public String toString() {
        // YOUR CODE HERE
        String subject = this.subject;

        if (this.startTime == null) {
            System.err.println("Invalid start time, Need a start time to fetch date.");
            return null;
        }
        LocalDateTime date = this.startTime;
        int duration = getDurationInMinutes();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = startTime.format(formatter);

        String message = String.format("%s study session on %s for %d minutes", subject, dateStr, duration);

        return message;
    }
}