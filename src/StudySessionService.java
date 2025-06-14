import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class that handles the business logic for study sessions
 * This is where we put rules, validations, and complex operations
 */
public class StudySessionService {
    private final StudySessionRepository repository;
    
    // Dependency Injection - we depend on the interface, not implementation
    public StudySessionService(StudySessionRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Creates a new study session with validation
     */
    public StudySession createStudySession(String subject, int durationMinutes, 
                                         StudySession.DifficultyLevel difficulty) {
        // Business rule: minimum study session is 5 minutes
        if (durationMinutes < 5) {
            throw new IllegalArgumentException("Study sessions must be at least 5 minutes long");
        }
        
        // Business rule: maximum study session is 8 hours
        if (durationMinutes > 480) {
            throw new IllegalArgumentException("Study sessions cannot exceed 8 hours");
        }
        
        StudySession session = new StudySession(subject, durationMinutes, difficulty);
        return repository.save(session);
    }
    
    /**
     * Gets all study sessions, formatted for display
     */
    public List<StudySession> getAllStudySessions() {
        return repository.findAll();
    }
    
    /**
     * Finds sessions by subject (case-insensitive)
     */
    public List<StudySession> getSessionsBySubject(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be empty");
        }
        return repository.findBySubject(subject.trim());
    }
    
    /**
     * Updates an existing study session
     */
    public boolean updateStudySession(StudySession session) {
        if (session.getId() <= 0) {
            throw new IllegalArgumentException("Cannot update session without valid ID");
        }
        
        // Verify the session exists
        StudySession existing = repository.findById(session.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Study session not found");
        }
        
        return repository.update(session);
    }
    
    /**
     * Deletes a study session
     */
    public boolean deleteStudySession(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid session ID");
        }
        
        return repository.delete(id);
    }
    
    /**
     * Generates a progress report
     */
    public StudyProgressReport generateProgressReport() {
        int todayMinutes = repository.getTotalStudyMinutesToday();
        int weekMinutes = repository.getTotalStudyMinutesThisWeek();
        List<StudySession> allSessions = repository.findAll();
        
        return new StudyProgressReport(todayMinutes, weekMinutes, allSessions);
    }
    
    /**
     * Gets study statistics by subject
     */
    public Map<String, Integer> getStudyTimeBySubject() {
        List<StudySession> allSessions = repository.findAll();
        Map<String, Integer> subjectTotals = new HashMap<>();
        
        for (StudySession session : allSessions) {
            String subject = session.getSubject();
            subjectTotals.merge(subject, session.getDurationMinutes(), Integer::sum);
        }
        
        return subjectTotals;
    }
    
    /**
     * Calculates study streak (consecutive days with study sessions)
     */
    public int calculateCurrentStudyStreak() {
        List<StudySession> sessions = repository.findAll();
        if (sessions.isEmpty()) {
            return 0;
        }
        
        // Sort by date and calculate consecutive days
        sessions.sort((a, b) -> b.getStartTime().compareTo(a.getStartTime()));
        
        Set<String> studyDates = new HashSet<>();
        for (StudySession session : sessions) {
            studyDates.add(session.getStartTime().toLocalDate().toString());
        }
        
        // Count consecutive days from today backwards
        LocalDateTime current = LocalDateTime.now();
        int streak = 0;
        
        while (studyDates.contains(current.toLocalDate().toString())) {
            streak++;
            current = current.minusDays(1);
        }
        
        return streak;
    }
}
