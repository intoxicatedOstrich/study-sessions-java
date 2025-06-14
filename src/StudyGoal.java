import java.time.LocalDateTime;

/**
 * Represents a study goal - separate concern from StudySession
 */
public class StudyGoal {
    private int id;
    private String description;
    private int targetMinutesPerDay;
    private int targetMinutesPerWeek;
    private LocalDateTime createdDate;
    private boolean isActive;
    
    public StudyGoal(String description, int targetMinutesPerDay, int targetMinutesPerWeek) {
        this.description = description;
        this.targetMinutesPerDay = targetMinutesPerDay;
        this.targetMinutesPerWeek = targetMinutesPerWeek;
        this.createdDate = LocalDateTime.now();
        this.isActive = true;
    }
    
    // Constructor for loading from database
    public StudyGoal(int id, String description, int targetMinutesPerDay, 
                    int targetMinutesPerWeek, LocalDateTime createdDate, boolean isActive) {
        this.id = id;
        this.description = description;
        this.targetMinutesPerDay = targetMinutesPerDay;
        this.targetMinutesPerWeek = targetMinutesPerWeek;
        this.createdDate = createdDate;
        this.isActive = isActive;
    }
    
    // Getters and setters with validation
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Goal description cannot be empty");
        }
        this.description = description.trim();
    }
    
    public int getTargetMinutesPerDay() { return targetMinutesPerDay; }
    public void setTargetMinutesPerDay(int targetMinutesPerDay) {
        if (targetMinutesPerDay < 0) {
            throw new IllegalArgumentException("Target minutes cannot be negative");
        }
        this.targetMinutesPerDay = targetMinutesPerDay;
    }
    
    public int getTargetMinutesPerWeek() { return targetMinutesPerWeek; }
    public void setTargetMinutesPerWeek(int targetMinutesPerWeek) {
        if (targetMinutesPerWeek < 0) {
            throw new IllegalArgumentException("Target minutes cannot be negative");
        }
        this.targetMinutesPerWeek = targetMinutesPerWeek;
    }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
}
