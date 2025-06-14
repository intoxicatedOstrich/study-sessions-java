package com.studytracker;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudyTracker {
    private ArrayList<StudySession> sessions;
    
    public StudyTracker() {
        this.sessions = new ArrayList<>();
    }
    
    // Add a new study session
    public void addSession(StudySession session) {
        sessions.add(session);
    }
    
    // Get all sessions
    public ArrayList<StudySession> getAllSessions() {
        return new ArrayList<>(sessions); // Return a copy to prevent external modification
    }
    
    // TODO: Implement getTotalStudyMinutes() - sum up all session durations
    public int getTotalStudyMinutes() {
        // YOUR CODE HERE
        // Hint: Loop through all sessions and sum their durations
        return 0;
    }
    
    // TODO: Implement getSessionsBySubject(String subject) 
    // Return all sessions that match the given subject
    public ArrayList<StudySession> getSessionsBySubject(String subject) {
        // YOUR CODE HERE
        // Hint: Create a new ArrayList, loop through sessions, add matching ones
        return new ArrayList<>();
    }
    
    // TODO: Implement getSessionsOnDate(LocalDate date)
    // Return all sessions that happened on the given date
    public ArrayList<StudySession> getSessionsOnDate(LocalDate date) {
        // YOUR CODE HERE  
        // Hint: Use startTime.toLocalDate() to get the date from a LocalDateTime
        return new ArrayList<>();
    }
    
    // TODO: Implement getAverageSessionLength() in minutes
    // Return 0 if there are no sessions
    public double getAverageSessionLength() {
        // YOUR CODE HERE
        return 0.0;
    }
    
    // TODO: CHALLENGE - Implement getStudyStreak()
    // A streak is consecutive days with at least one study session
    // Return the current streak count
    // Hint: Sort sessions by date first, then check for consecutive days
    public int getStudyStreak() {
        // YOUR CODE HERE
        return 0;
    }
}