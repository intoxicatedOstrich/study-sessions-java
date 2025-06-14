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
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (StudySession session : sessions) {
                // We'll save the basic info - students will need to parse this
                writer.println(session.getStartTime() + "," + 
                             session.getEndTime() + "," +
                             session.getSubject());
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
    
    // TODO: Implement loadSessions() method
    // Read the file and recreate StudySession objects
    // Hint: Use BufferedReader and split each line by comma
    public ArrayList<StudySession> loadSessions() {
        ArrayList<StudySession> sessions = new ArrayList<>();
        // YOUR CODE HERE
        // Hint: 
        // 1. Create a BufferedReader with new FileReader(filename)
        // 2. Read each line
        // 3. Split by comma
        // 4. Parse LocalDateTime with LocalDateTime.parse()
        // 5. Create new StudySession and add to list
        return sessions;
    }
}