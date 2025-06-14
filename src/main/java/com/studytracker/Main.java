package com.studytracker;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudyTracker tracker = new StudyTracker();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to Study Tracker!");
        System.out.println("This is a demo - your job is to implement the TODO methods");
        
        // Demo: Create a few study sessions
        LocalDateTime now = LocalDateTime.now();
        StudySession mathSession = new StudySession(
            now.minusHours(2), 
            now.minusHours(1), 
            "Math"
        );
        
        StudySession csSession = new StudySession(
            now.minusHours(4),
            now.minusHours(3),
            "Computer Science"
        );
        
        tracker.addSession(mathSession);
        tracker.addSession(csSession);
        
        // Test your implementations
        System.out.println("\nTesting your implementations:");
        System.out.println("Total study time: " + tracker.getTotalStudyMinutes() + " minutes");
        System.out.println("Average session length: " + tracker.getAverageSessionLength() + " minutes");
        System.out.println("Current study streak: " + tracker.getStudyStreak() + " days");
        
        // TODO: Add a simple menu loop here
        // 1. Add new session
        // 2. View all sessions  
        // 3. View sessions by subject
        // 4. View statistics
        // 5. Save and exit
        
        scanner.close();
    }
}