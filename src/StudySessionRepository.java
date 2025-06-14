import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface defining what operations we can do with StudySessions
 * This is the Repository pattern - separates data access from business logic
 */
public interface StudySessionRepository {
    StudySession save(StudySession session);
    StudySession findById(int id);
    List<StudySession> findAll();
    List<StudySession> findBySubject(String subject);
    List<StudySession> findByDateRange(LocalDateTime start, LocalDateTime end);
    boolean update(StudySession session);
    boolean delete(int id);
    int getTotalStudyMinutesToday();
    int getTotalStudyMinutesThisWeek();
}
