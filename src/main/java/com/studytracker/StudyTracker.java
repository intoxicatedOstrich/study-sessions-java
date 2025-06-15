package com.studytracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        if (date == null || sessions.isEmpty()) {
            return sessionsOnDate;
        }
        for (StudySession s : sessions) {
            if (s != null && s.getStartTime() != null && s.getStartTime().toLocalDate().equals(date)) {
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

    // Implement getStudyStreak()
    // A streak is consecutive days with at least one study session
    // Return the current streak count
    public int getStudyStreak() {
        if (sessions.isEmpty()) {
            return 0;
        }

        // Get all unique study dates
        ArrayList<LocalDate> studyDates = new ArrayList<>();
        for (StudySession session : sessions) {
            if (session != null && session.getStartTime() != null) {
                LocalDate date = session.getStartTime().toLocalDate();
                if (!studyDates.contains(date)) {
                    studyDates.add(date);
                }
            }
        }

        if (studyDates.isEmpty()) {
            return 0;
        }

        // Sort dates in descending order (most recent first)
        Collections.sort(studyDates, Collections.reverseOrder());

        // Start with the most recent date
        LocalDate currentDate = studyDates.get(0);
        int streak = 1;

        // Check consecutive days going backwards
        for (int i = 1; i < studyDates.size(); i++) {
            LocalDate nextDate = studyDates.get(i);
            // If the next date is exactly one day before the current date
            if (nextDate.equals(currentDate.minusDays(1))) {
                streak++;
                currentDate = nextDate;
            } else {
                // Gap found, streak is broken
                break;
            }
        }

        return streak;
    }
}