import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SQLite implementation of the repository
 * This class knows HOW to store data, but not WHAT the data means
 */
public class SQLiteStudySessionRepository implements StudySessionRepository {
    private final String dbPath;

    public SQLiteStudySessionRepository(String dbPath) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC driver not found", e);
        }
        this.dbPath = dbPath;
        initializeDatabase();
    }

    /**
     * Sets up the database schema if it doesn't exist
     * This is called when the repository is created
     */
    private void initializeDatabase() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS study_sessions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    subject TEXT NOT NULL,
                    duration_minutes INTEGER NOT NULL,
                    start_time TEXT NOT NULL,
                    notes TEXT,
                    tags TEXT,
                    difficulty TEXT NOT NULL
                )
                """;

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    /**
     * Gets a database connection
     * Private method - implementation detail
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    @Override
    public StudySession save(StudySession session) {
        String sql = """
                INSERT INTO study_sessions (subject, duration_minutes, start_time, notes, tags, difficulty)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, session.getSubject());
            pstmt.setInt(2, session.getDurationMinutes());
            pstmt.setString(3, session.getStartTime().toString());
            pstmt.setString(4, session.getNotes());
            pstmt.setString(5, String.join(",", session.getTags()));
            pstmt.setString(6, session.getDifficulty().name());

            pstmt.executeUpdate();

            // Get the generated ID and set it on our object
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    session.setId(rs.getInt(1));
                }
            }

            return session;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save study session", e);
        }
    }

    @Override
    public StudySession findById(int id) {
        String sql = "SELECT * FROM study_sessions WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToStudySession(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find study session", e);
        }
    }

    @Override
    public List<StudySession> findAll() {
        String sql = "SELECT * FROM study_sessions ORDER BY start_time DESC";
        List<StudySession> sessions = new ArrayList<>();

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sessions.add(mapResultSetToStudySession(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve study sessions", e);
        }

        return sessions;
    }

    @Override
    public List<StudySession> findBySubject(String subject) {
        String sql = "SELECT * FROM study_sessions WHERE LOWER(subject) = LOWER(?) ORDER BY start_time DESC";
        List<StudySession> sessions = new ArrayList<>();

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, subject);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sessions.add(mapResultSetToStudySession(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find sessions by subject", e);
        }

        return sessions;
    }

    @Override
    public List<StudySession> findByDateRange(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM study_sessions WHERE start_time BETWEEN ? AND ? ORDER BY start_time DESC";
        List<StudySession> sessions = new ArrayList<>();

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, start.toString());
            pstmt.setString(2, end.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sessions.add(mapResultSetToStudySession(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find sessions by date range", e);
        }

        return sessions;
    }

    @Override
    public boolean update(StudySession session) {
        String sql = """
                UPDATE study_sessions
                SET subject = ?, duration_minutes = ?, start_time = ?, notes = ?, tags = ?, difficulty = ?
                WHERE id = ?
                """;

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, session.getSubject());
            pstmt.setInt(2, session.getDurationMinutes());
            pstmt.setString(3, session.getStartTime().toString());
            pstmt.setString(4, session.getNotes());
            pstmt.setString(5, String.join(",", session.getTags()));
            pstmt.setString(6, session.getDifficulty().name());
            pstmt.setInt(7, session.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update study session", e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM study_sessions WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete study session", e);
        }
    }

    @Override
    public int getTotalStudyMinutesToday() {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime tomorrow = today.plusDays(1);

        String sql = "SELECT COALESCE(SUM(duration_minutes), 0) FROM study_sessions WHERE start_time >= ? AND start_time < ?";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, today.toString());
            pstmt.setString(2, tomorrow.toString());

            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get today's study time", e);
        }
    }

    @Override
    public int getTotalStudyMinutesThisWeek() {
        LocalDateTime startOfWeek = LocalDateTime.now().toLocalDate().atStartOfDay()
                .minusDays(LocalDateTime.now().getDayOfWeek().getValue() - 1);
        LocalDateTime startOfNextWeek = startOfWeek.plusWeeks(1);

        String sql = "SELECT COALESCE(SUM(duration_minutes), 0) FROM study_sessions WHERE start_time >= ? AND start_time < ?";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, startOfWeek.toString());
            pstmt.setString(2, startOfNextWeek.toString());

            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get this week's study time", e);
        }
    }

    /**
     * Helper method to convert database rows to StudySession objects
     * This is where we handle the data type conversions
     */
    private StudySession mapResultSetToStudySession(ResultSet rs) throws SQLException {
        List<String> tags = new ArrayList<>();
        String tagsString = rs.getString("tags");
        if (tagsString != null && !tagsString.trim().isEmpty()) {
            tags = Arrays.asList(tagsString.split(","));
        }

        return new StudySession(
                rs.getInt("id"),
                rs.getString("subject"),
                rs.getInt("duration_minutes"),
                LocalDateTime.parse(rs.getString("start_time")),
                rs.getString("notes"),
                tags,
                StudySession.DifficultyLevel.valueOf(rs.getString("difficulty")));
    }
}
