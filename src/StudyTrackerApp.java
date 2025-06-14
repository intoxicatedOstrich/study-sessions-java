/**
 * Main application class - the entry point
 * This class wires everything together (Dependency Injection)
 */
public class StudyTrackerApp {
    public static void main(String[] args) {
        try {
            // Set up the layers of our application
            // Repository layer - handles data persistence
            StudySessionRepository repository = new SQLiteStudySessionRepository("study_tracker.db");
            
            // Service layer - handles business logic
            StudySessionService service = new StudySessionService(repository);
            
            // Presentation layer - handles user interaction
            StudyTrackerCLI cli = new StudyTrackerCLI(service);
            
            // Start the application
            cli.start();
            
        } catch (Exception e) {
            System.err.println("💥 Fatal error starting application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
