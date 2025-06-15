package com.studytracker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudyTracker tracker = new StudyTracker();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Study Tracker!");
        System.out.println("This is a demo - your job is to implement the TODO methods");

        // TODO: Add a simple menu loop here
        // 1. Add new session
        // 2. View all sessions
        // 3. View sessions by subject
        // 4. View statistics
        // 5. Save and exit

        boolean isRunning = true;

        while (isRunning) {
            // Display menu
            System.out.println("\n=== Study Session Tracker ===");
            System.out.println("1. Add new session");
            System.out.println("2. View all sessions");
            System.out.println("3. View sessions by subject");
            System.out.println("4. View statistics");
            System.out.println("5. Save and exit");
            System.out.println("======================");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline after number input

            switch (choice) {
                case 1:
                    // TODO: Implement add new session
                    System.out.println("Adding new session...");
                    System.out.println("choice ( + Enter)");
                    System.out.println("======================");

                    // Step 1: Get the subject name from the user
                    // Hint: Use scanner.nextLine() to read a string
                    // Example: "Math", "Computer Science", "Physics"
                    System.out.println("What was the subject ? ");
                    System.out.println("======================");
                    System.out.println("choice ( + Enter)");
                    String subject = scanner.nextLine();

                    // Step 2: Determine when the session starts
                    // Option A: Start right now - use LocalDateTime.now()
                    // Option B: Let user enter a past session - ask how long ago
                    // Hint: You can use LocalDateTime.now().minusHours(n) for past times
                    while (subject == null) {
                        System.err.println("Must enter a valid subject.");
                        System.out.println("choice ( + Enter)");
                        subject = scanner.nextLine();
                    }
                    System.out.println("======================");
                    System.out.println("Are you starting now or is this a past session?");
                    System.out.println("1: Now");
                    System.out.println("2: Past session");

                    int option = scanner.nextInt();
                    LocalDateTime startTime = LocalDateTime.now();
                    LocalDateTime endTime;
                    while (option != 1 && option != 2) {
                        System.out.println("Please seleft a valid option");
                        System.out.println("======================");
                        option = scanner.nextInt();
                        scanner.nextLine();
                    }
                    if (option == 2) {
                        System.out.println("How many hours ago did you start?");
                        System.out.println("======================");
                        int hoursAgo = scanner.nextInt();
                        startTime = LocalDateTime.now().minusHours(hoursAgo);
                    }
                    scanner.nextLine();
                    // Step 3: Get the duration of the study session
                    // Ask user: "How many minutes did you study?"
                    // Hint: Use scanner.nextInt() to read a number
                    // Don't forget scanner.nextLine() after nextInt() to clear the buffer!
                    System.out.println("======================");
                    System.out.println("How many minutes did you study?");
                    System.out.println("======================");
                    System.out.println("Duration ( + enter )");
                    int duration = scanner.nextInt();
                    // Step 4: Calculate the end time
                    // Hint: Use startTime.plusMinutes(duration) to add minutes to a LocalDateTime
                    while (duration <= 0) {
                        System.err.println("Please enter a positive duration:");
                        System.out.println("======================");
                        System.out.println("Duration ( + Enter )");
                        duration = scanner.nextInt();
                        scanner.nextLine();
                    }
                    endTime = startTime.plusMinutes(duration);
                    // Step 5: Create the StudySession object
                    // IMPORTANT: The constructor needs THREE parameters:
                    // StudySession(LocalDateTime startTime, LocalDateTime endTime, String subject)
                    StudySession session = new StudySession(startTime, endTime, subject);
                    // This is why your current code fails - you're calling new StudySession() with
                    // no parameters!
                    System.out.println("Would you like to do any of the following?");
                    System.out.println("======================");
                    System.out.println("1. Add Notes");
                    System.out.println("2. Add Difficulty");
                    System.out.println("3. Add Notes AND difficulty");
                    System.out.println("0. Nah I'm good thanks.");
                    System.out.println("======================");
                    System.out.println("Select Option (+ Enter): ");

                    int notesAndOrDifficulty = scanner.nextInt();
                    scanner.nextLine();

                    while (notesAndOrDifficulty < 0 && notesAndOrDifficulty > 3) {
                        System.out.println("Please select 0, 1, 2, or 3.");
                        System.out.println("======================");
                        System.out.println("1. Add Notes");
                        System.out.println("2. Add Difficulty");
                        System.out.println("3. Add Notes AND difficulty");
                        System.out.println("0. Nah I'm good thanks.");
                        System.out.println("======================");
                        System.out.println("Select Option (+ Enter): ");
                        notesAndOrDifficulty = scanner.nextInt();
                        scanner.nextLine();
                    }

                    if (notesAndOrDifficulty == 1 || notesAndOrDifficulty == 3) {
                        System.out.println("Write your notes here:");
                        String notes = scanner.nextLine();
                        session.setNotes(notes);
                        scanner.nextLine();
                    }

                    if (notesAndOrDifficulty == 2 || notesAndOrDifficulty == 3) {
                        System.out.println("Score difficulty on a scale of 1 to 5");
                        int difficulty = scanner.nextInt();
                        session.setDifficulty(difficulty);
                        scanner.nextLine();
                    }

                    // Step 7: Add the session to the tracker
                    // tracker.addSession(session);
                    tracker.addSession(session);
                    // Step 8: Confirm to the user
                    // Print a success message
                    System.out.println("======================");
                    System.out.println(" New Session Added: " + session.toString());
                    System.out.println("======================");
                    break;
                case 2:
                    // TODO: Implement view all sessions
                    System.out.println("Viewing all sessions...");
                    for (StudySession s : tracker.getAllSessions()) {
                        System.out.println(s.toString());
                    }
                    break;
                case 3:
                    // TODO: Implement view by subject
                    System.out.println("Viewing sessions by subject...");
                    ArrayList<StudySession> sessionsBySubject = tracker.getSessionsBySubject(scanner.nextLine());
                    for (StudySession s : sessionsBySubject) {
                        System.out.println(s.toString());
                    }
                    break;
                case 4:
                    // TODO: Implement view statistics
                    System.out.println("Viewing statistics...");
                    System.out.println("Total Study Minutes: ");
                    System.out.print(tracker.getTotalStudyMinutes());
                    System.out.println("Average Session Lenght: ");
                    System.out.print(tracker.getAverageSessionLength());
                    System.out.println("Length of Study Streak: ");
                    System.out.print(tracker.getStudyStreak());
                    break;
                case 5:
                    // TODO: Implement save functionality
                    System.out.println("Saving and exiting...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
            scanner.close();
        }
    }

}