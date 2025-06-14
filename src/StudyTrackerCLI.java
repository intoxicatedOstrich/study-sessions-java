import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Command-line interface for the Study Session Tracker
 * This class handles user input/output and coordinates with the service layer
 */
public class StudyTrackerCLI {
    private final StudySessionService service;
    private final Scanner scanner;
    
    public StudyTrackerCLI(StudySessionService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Main menu loop
     */
    public void start() {
        System.out.println("📚 Welcome to Study Session Tracker! 📚");
        System.out.println("=====================================");
        
        while (true) {
            showMainMenu();
            String choice = scanner.nextLine().trim();
            
            try {
                switch (choice) {
                    case "1" -> createNewSession();
                    case "2" -> viewAllSessions();
                    case "3" -> searchSessions();
                    case "4" -> generateReport();
                    case "5" -> showStatistics();
                    case "6" -> deleteSession();
                    case "7" -> {
                        System.out.println("Happy studying! 📖");
                        return;
                    }
                    default -> System.out.println("❌ Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("📋 MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. 📝 Create New Study Session");
        System.out.println("2. 📖 View All Sessions");
        System.out.println("3. 🔍 Search Sessions by Subject");
        System.out.println("4. 📊 Generate Progress Report");
        System.out.println("5. 📈 View Statistics");
        System.out.println("6. 🗑️  Delete Session");
        System.out.println("7. 🚪 Exit");
        System.out.println("=".repeat(40));
        System.out.print("Choose an option (1-7): ");
    }
    
    private void createNewSession() {
        System.out.println("\n📝 CREATE NEW STUDY SESSION");
        System.out.println("-".repeat(30));
        
        System.out.print("Subject: ");
        String subject = scanner.nextLine().trim();
        
        if (subject.isEmpty()) {
            System.out.println("❌ Subject cannot be empty!");
            return;
        }
        
        System.out.print("Duration (minutes): ");
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
            return;
        }
        
        System.out.println("Difficulty Level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.println("4. Very Hard");
        System.out.print("Choose difficulty (1-4): ");
        
        StudySession.DifficultyLevel difficulty;
        String diffChoice = scanner.nextLine().trim();
        
        switch (diffChoice) {
            case "1" -> difficulty = StudySession.DifficultyLevel.EASY;
            case "2" -> difficulty = StudySession.DifficultyLevel.MEDIUM;
            case "3" -> difficulty = StudySession.DifficultyLevel.HARD;
            case "4" -> difficulty = StudySession.DifficultyLevel.VERY_HARD;
            default -> {
                System.out.println("❌ Invalid difficulty choice!");
                return;
            }
        }
        
        try {
            StudySession session = service.createStudySession(subject, duration, difficulty);
            System.out.println("✅ Study session created successfully!");
            System.out.println("📋 " + session);
            
            // Optional: Add notes and tags
            System.out.print("\nAdd notes (optional): ");
            String notes = scanner.nextLine();
            if (!notes.trim().isEmpty()) {
                session.setNotes(notes);
                service.updateStudySession(session);
            }
            
            System.out.print("Add tags (comma-separated, optional): ");
            String tagsInput = scanner.nextLine();
            if (!tagsInput.trim().isEmpty()) {
                String[] tags = tagsInput.split(",");
                for (String tag : tags) {
                    session.addTag(tag.trim());
                }
                service.updateStudySession(session);
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    private void viewAllSessions() {
        System.out.println("\n📖 ALL STUDY SESSIONS");
        System.out.println("-".repeat(50));
        
        List<StudySession> sessions = service.getAllStudySessions();
        
        if (sessions.isEmpty()) {
            System.out.println("📭 No study sessions found. Create your first session!");
            return;
        }
        
        for (int i = 0; i < sessions.size(); i++) {
            StudySession session = sessions.get(i);
            System.out.printf("%d. %s - %d mins (%s) [%s]%n", 
                i + 1, 
                session.getSubject(), 
                session.getDurationMinutes(),
                session.getDifficulty(),
                session.getFormattedStartTime()
            );
            
            if (!session.getNotes().isEmpty()) {
                System.out.println("   📝 Notes: " + session.getNotes());
            }
            
            if (!session.getTags().isEmpty()) {
                System.out.println("   🏷️  Tags: " + String.join(", ", session.getTags()));
            }
            System.out.println();
        }
    }
    
    private void searchSessions() {
        System.out.println("\n🔍 SEARCH SESSIONS BY SUBJECT");
        System.out.println("-".repeat(35));
        
        System.out.print("Enter subject to search: ");
        String subject = scanner.nextLine().trim();
        
        if (subject.isEmpty()) {
            System.out.println("❌ Search term cannot be empty!");
            return;
        }
        
        List<StudySession> sessions = service.getSessionsBySubject(subject);
        
        if (sessions.isEmpty()) {
            System.out.println("📭 No sessions found for subject: " + subject);
            return;
        }
        
        System.out.println("📋 Found " + sessions.size() + " session(s) for '" + subject + "':");
        System.out.println("-".repeat(50));
        
        for (StudySession session : sessions) {
            System.out.printf("• %s - %d mins (%s) [%s]%n",
                session.getSubject(),
                session.getDurationMinutes(),
                session.getDifficulty(),
                session.getFormattedStartTime()
            );
        }
    }
    
    private void generateReport() {
        System.out.println("\n📊 GENERATING PROGRESS REPORT");
        System.out.println("-".repeat(35));
        
        StudyProgressReport report = service.generateProgressReport();
        System.out.println(report.getFormattedReport());
        
        // Show study streak
        int streak = service.calculateCurrentStudyStreak();
        if (streak > 0) {
            System.out.println("🔥 Current Study Streak: " + streak + " day(s)!");
        } else {
            System.out.println("💡 Start your study streak today!");
        }
    }
    
    private void showStatistics() {
        System.out.println("\n📈 STUDY STATISTICS");
        System.out.println("-".repeat(25));
        
        Map<String, Integer> subjectStats = service.getStudyTimeBySubject();
        
        if (subjectStats.isEmpty()) {
            System.out.println("📭 No study data available yet.");
            return;
        }
        
        System.out.println("📊 Time spent by subject:");
        subjectStats.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> {
                double hours = entry.getValue() / 60.0;
                System.out.printf("  %s: %d minutes (%.1f hours)%n", 
                    entry.getKey(), entry.getValue(), hours);
            });
    }
    
    private void deleteSession() {
        System.out.println("\n🗑️ DELETE STUDY SESSION");
        System.out.println("-".repeat(25));
        
        List<StudySession> sessions = service.getAllStudySessions();
        
        if (sessions.isEmpty()) {
            System.out.println("📭 No sessions to delete.");
            return;
        }
        
        System.out.println("📋 Select session to delete:");
        for (int i = 0; i < sessions.size(); i++) {
            StudySession session = sessions.get(i);
            System.out.printf("%d. %s - %d mins [%s]%n", 
                i + 1, 
                session.getSubject(), 
                session.getDurationMinutes(),
                session.getFormattedStartTime()
            );
        }
        
        System.out.print("Enter session number (0 to cancel): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            if (choice == 0) {
                System.out.println("❌ Deletion cancelled.");
                return;
            }
            
            if (choice < 1 || choice > sessions.size()) {
                System.out.println("❌ Invalid session number!");
                return;
            }
            
            StudySession sessionToDelete = sessions.get(choice - 1);
            System.out.printf("⚠️ Are you sure you want to delete '%s'? (y/N): ", 
                sessionToDelete.getSubject());
            
            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (confirmation.equals("y") || confirmation.equals("yes")) {
                if (service.deleteStudySession(sessionToDelete.getId())) {
                    System.out.println("✅ Session deleted successfully!");
                } else {
                    System.out.println("❌ Failed to delete session.");
                }
            } else {
                System.out.println("❌ Deletion cancelled.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
}
