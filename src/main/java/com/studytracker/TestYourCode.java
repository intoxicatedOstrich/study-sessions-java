package com.studytracker;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class TestYourCode {
    public static void main(String[] args) {
        System.out.println("=== Testing Your Study Tracker Implementation ===\n");
        
        // Test 1: StudySession duration calculation
        System.out.println("Test 1: Duration Calculation");
        LocalDateTime start = LocalDateTime.of(2024, 1, 15, 14, 0); // 2:00 PM
        LocalDateTime end = LocalDateTime.of(2024, 1, 15, 15, 30);  // 3:30 PM
        StudySession testSession = new StudySession(start, end, "Math");
        System.out.println("Expected duration: 90 minutes");
        System.out.println("Your result: " + testSession.getDurationInMinutes() + " minutes");
        System.out.println();
        
        // Test 2: StudyTracker total minutes
        System.out.println("Test 2: Total Study Minutes");
        StudyTracker tracker = new StudyTracker();
        tracker.addSession(new StudySession(
            LocalDateTime.now().minusHours(2),
            LocalDateTime.now().minusHours(1),
            "Physics"
        )); // 60 minutes
        tracker.addSession(new StudySession(
            LocalDateTime.now().minusHours(4),
            LocalDateTime.now().minusHours(3),
            "Chemistry"
        )); // 60 minutes
        System.out.println("Expected total: 120 minutes");
        System.out.println("Your result: " + tracker.getTotalStudyMinutes() + " minutes");
        System.out.println();
        
        // Test 3: Filter by subject
        System.out.println("Test 3: Filter by Subject");
        tracker.addSession(new StudySession(
            LocalDateTime.now().minusHours(6),
            LocalDateTime.now().minusHours(5),
            "Physics"
        ));
        System.out.println("Expected Physics sessions: 2");
        System.out.println("Your result: " + tracker.getSessionsBySubject("Physics").size());
        System.out.println();
        
        // Test 4: Average session length
        System.out.println("Test 4: Average Session Length");
        System.out.println("Expected average: 60.0 minutes");
        System.out.println("Your result: " + tracker.getAverageSessionLength() + " minutes");
        System.out.println();
        
        // Add more tests as you implement features!
        System.out.println("=== Keep testing as you code! ===");
    }
}