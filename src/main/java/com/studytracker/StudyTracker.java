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

    public int getTotalStudyMinutes() {
        // YOUR CODE HERE
        // Hint: Loop through all sessions and sum their durations
        int duration = 0;
        for (int i = 0; i < sessions.size(); i++) {
            duration += sessions.get(i).getDurationInMinutes();
        }
        return duration;
    }

    // Return all sessions that match the given subject
    public ArrayList<StudySession> getSessionsBySubject(String subject) {
        ArrayList<StudySession> sessionsBySubjectList = new ArrayList<>();
        for (StudySession s : sessions) {
            if (s.getSubject() == null || s.getSubject().equals(subject)) {
                sessionsBySubjectList.add(s);
            }
        }
        return sessionsBySubjectList;
    }

    // TODO: Implement getSessionsOnDate(LocalDate date)
    // Return all sessions that happened on the given date

    public ArrayList<StudySession> getSessionsOnDate(LocalDate date) {
        ArrayList<StudySession> sessionsOnDate = new ArrayList<>();
        if (sessions.isEmpty()) {
            System.out.println("No Sessions on this day...");
            return sessionsOnDate;
        }
        for (StudySession s : sessions) {
            if (s.getStartTime().toLocalDate().equals(date)) {
                sessionsOnDate.add(s);
            }
        }
        return sessionsOnDate;
    }

    public double getAverageSessionLength() {
        if (sessions.isEmpty()) {
            return 0.0;
        }
        double totalMinutes = 0;
        for (StudySession s : sessions) {
            totalMinutes += s.getDurationInMinutes();
        }
        return (double) totalMinutes / sessions.size();
    }

    // TODO: CHALLENGE - Implement getStudyStreak()
    // A streak is consecutive days with at least one study session
    // Return the current streak count
    // Hint: Sort sessions by date first, then check for consecutive days
    public int getStudyStreak() {
        LocalDate currentDate = LocalDate.now();
        if (getSessionsOnDate(currentDate).isEmpty()) {
            return 0;
        }
        int streak = 0;

        while (!getSessionsOnDate(currentDate.minusDays(streak)).isEmpty()) {
            streak++;
        }
        return streak;
    }
}