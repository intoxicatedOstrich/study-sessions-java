import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Data class for progress reports
 */
public class StudyProgressReport {
    private final int todayMinutes;
    private final int weekMinutes;
    private final int totalSessions;
    private final double averageSessionLength;
    private final String mostStudiedSubject;
    
    public StudyProgressReport(int todayMinutes, int weekMinutes, List<StudySession> allSessions) {
        this.todayMinutes = todayMinutes;
        this.weekMinutes = weekMinutes;
        this.totalSessions = allSessions.size();
        
        if (allSessions.isEmpty()) {
            this.averageSessionLength = 0;
            this.mostStudiedSubject = "None";
        } else {
            this.averageSessionLength = allSessions.stream()
                    .mapToInt(StudySession::getDurationMinutes)
                    .average()
                    .orElse(0);
            
            this.mostStudiedSubject = allSessions.stream()
                    .collect(Collectors.groupingBy(StudySession::getSubject, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("None");
        }
    }
    
    // Getters
    public int getTodayMinutes() { return todayMinutes; }
    public int getWeekMinutes() { return weekMinutes; }
    public int getTotalSessions() { return totalSessions; }
    public double getAverageSessionLength() { return averageSessionLength; }
    public String getMostStudiedSubject() { return mostStudiedSubject; }
    
    public String getFormattedReport() {
        return String.format("""
            📊 STUDY PROGRESS REPORT 📊
            ═══════════════════════════
            Today's Study Time: %d minutes (%.1f hours)
            This Week's Total: %d minutes (%.1f hours)
            Total Sessions: %d
            Average Session: %.1f minutes
            Most Studied Subject: %s
            ═══════════════════════════
            """, 
            todayMinutes, todayMinutes / 60.0,
            weekMinutes, weekMinutes / 60.0,
            totalSessions,
            averageSessionLength,
            mostStudiedSubject);
    }
}
