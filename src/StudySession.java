import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Represents a single study session
 * This is a POJO (Plain Old Java Object) with encapsulation
 */
public class StudySession {
    // Private fields - encapsulation in action
    private int id;
    private String subject;
    private int durationMinutes;
    private LocalDateTime startTime;
    private String notes;
    private List<String> tags;
    private DifficultyLevel difficulty;
    
    // Enum for type safety - much better than string constants
    public enum DifficultyLevel {
        EASY, MEDIUM, HARD, VERY_HARD
    }
    
    // Constructor - how we create objects
    public StudySession(String subject, int durationMinutes, DifficultyLevel difficulty) {
        this.subject = subject;
        this.durationMinutes = durationMinutes;
        this.difficulty = difficulty;
        this.startTime = LocalDateTime.now();
        this.tags = new ArrayList<>();
        this.notes = "";
    }
    
    // Constructor for loading from database (includes ID)
    public StudySession(int id, String subject, int durationMinutes, 
                       LocalDateTime startTime, String notes, 
                       List<String> tags, DifficultyLevel difficulty) {
        this.id = id;
        this.subject = subject;
        this.durationMinutes = durationMinutes;
        this.startTime = startTime;
        this.notes = notes != null ? notes : "";
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        this.difficulty = difficulty;
    }
    
    // Getters and Setters - controlled access to private fields
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { 
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be empty");
        }
        this.subject = subject.trim(); 
    }
    
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        this.durationMinutes = durationMinutes;
    }
    
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes != null ? notes : ""; }
    
    public List<String> getTags() { 
        return new ArrayList<>(tags); // Return copy to prevent external modification
    }
    
    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty() && !tags.contains(tag.trim())) {
            tags.add(tag.trim().toLowerCase());
        }
    }
    
    public void removeTag(String tag) {
        tags.remove(tag.toLowerCase());
    }
    
    public DifficultyLevel getDifficulty() { return difficulty; }
    public void setDifficulty(DifficultyLevel difficulty) { this.difficulty = difficulty; }
    
    // Business logic methods
    public double getDurationHours() {
        return durationMinutes / 60.0;
    }
    
    public String getFormattedStartTime() {
        return startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
    
    // Override toString for easy debugging/display
    @Override
    public String toString() {
        return String.format("StudySession{id=%d, subject='%s', duration=%d mins, difficulty=%s, time=%s}",
                id, subject, durationMinutes, difficulty, getFormattedStartTime());
    }
    
    // Override equals and hashCode for proper object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StudySession that = (StudySession) obj;
        return id == that.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
