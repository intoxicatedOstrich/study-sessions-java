package com.studytracker;

import java.io.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class SimpleFileStorage {
    private String filename;

    public SimpleFileStorage(String filename) {
        this.filename = filename;
    }

    // Save all sessions to a text file
    // Format: startTime,endTime,subject,notes,difficulty
    public void saveSessions(ArrayList<StudySession> sessions) {
        // PrintWriter is a class that lets you write text to files easily
        // It has methods like println() that work just like System.out.println()
        // but write to a file instead of the console

        // This special syntax is called "try-with-resources" - it automatically
        // closes the file when we're done, even if an error occurs
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // FileWriter creates a connection to the file
            // PrintWriter wraps around FileWriter to give us convenient methods

            for (StudySession session : sessions) {
                // writer.println() writes a line to the file and adds a newline
                // We're creating CSV format (comma-separated values)
                writer.println(session.getStartTime() + "," +
                        session.getEndTime() + "," +
                        session.getSubject());
            }
            // The file is automatically saved and closed here!
        } catch (IOException e) {
            // IOException happens if the file can't be created or written to
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    // TODO: Implement loadSessions() method
    // Read the file and recreate StudySession objects
    // Hint: Use BufferedReader and split each line by comma
    public ArrayList<StudySession> loadSessions() {
        ArrayList<StudySession> sessions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    // Split by comma, but only into 3 parts maximum
                    // This handles subjects with commas in them
                    String[] parts = line.split(",", 3);
                    
                    // Check if we have all required parts
                    if (parts.length < 3) {
                        // Skip malformed lines (missing data)
                        continue;
                    }
                    
                    LocalDateTime start = LocalDateTime.parse(parts[0]);
                    LocalDateTime end = LocalDateTime.parse(parts[1]);
                    String subject = parts[2];
                    StudySession session = new StudySession(start, end, subject);
                    sessions.add(session);
                } catch (Exception e) {
                    // Skip lines that can't be parsed (invalid dates, etc.)
                    // Just continue to the next line
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading sessions: " + e.getMessage());
        }
        return sessions;
    }
}