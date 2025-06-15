# Study Tracker Challenge - COMP 250 Level

## Overview
Build a simple study session tracker that helps students log and analyze their study habits. This project will help you practice:
- Object-oriented programming (classes, methods, encapsulation)
- Working with ArrayLists
- Basic file I/O
- Date/time manipulation
- Input validation
- Simple algorithms

## Your Tasks

### 1. Complete the StudySession class (StudySession.java)
- [ ] Implement remaining getters and setters
- [ ] Add validation in setters (endTime > startTime, subject not empty, difficulty 1-5)
- [ ] Implement `getDurationInMinutes()` method
- [ ] Implement `toString()` method with nice formatting

### 2. Complete the StudyTracker class (StudyTracker.java)
- [ ] Implement `getTotalStudyMinutes()` - sum all session durations
- [ ] Implement `getSessionsBySubject(String subject)` - filter by subject
- [ ] Implement `getSessionsOnDate(LocalDate date)` - filter by date
- [ ] Implement `getAverageSessionLength()` - calculate average duration
- [ ] **CHALLENGE**: Implement `getStudyStreak()` - consecutive days with sessions

### 3. Complete the SimpleFileStorage class (SimpleFileStorage.java)
- [ ] Implement `loadSessions()` to read sessions from a text file
- [ ] Handle file not found gracefully (return empty list)
- [ ] Parse the comma-separated values correctly

### 4. Complete the Main class menu system (Main.java)
- [ ] Add a menu loop with options:
  1. Add new study session (get user input)
  2. View all sessions
  3. View sessions by subject
  4. View statistics (total time, average, streak)
  5. Save and exit
- [ ] Add input validation for user choices

## Hints and Tips

### Working with LocalDateTime
```java
// Create a LocalDateTime
LocalDateTime now = LocalDateTime.now();
LocalDateTime yesterday = now.minusDays(1);

// Parse from string
LocalDateTime parsed = LocalDateTime.parse("2024-01-15T10:30:00");

// Get just the date part
LocalDate dateOnly = now.toLocalDate();

// Calculate duration between times
Duration duration = Duration.between(startTime, endTime);
long minutes = duration.toMinutes();
```

### Working with ArrayLists
```java
// Create and add items
ArrayList<StudySession> sessions = new ArrayList<>();
sessions.add(newSession);

// Loop through items
for (StudySession session : sessions) {
    // do something with session
}

// Filter items (create new list with matching items)
ArrayList<StudySession> filtered = new ArrayList<>();
for (StudySession session : sessions) {
    if (session.getSubject().equals(targetSubject)) {
        filtered.add(session);
    }
}
```

### File I/O Basics
```java
// Reading from file
try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
    String line;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        // process parts
    }
} catch (IOException e) {
    // handle error
}

// Writing to file
try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
    writer.println("some text");
} catch (IOException e) {
    // handle error
}
```

## Testing Your Code
1. Run `./build_simple.sh` to compile
2. Run `java -cp build com.studytracker.Main` to test
3. Try different inputs and edge cases
4. Make sure validation works properly

## Bonus Challenges (if you finish early)
1. Add a method to find the most studied subject
2. Calculate study efficiency (difficulty × duration)
3. Add weekly/monthly statistics
4. Implement a simple recommendation system ("You haven't studied Math in 3 days!")

## Learning Goals
By completing this challenge, you'll practice:
- Decomposing problems into methods
- Working with collections (ArrayList)
- Basic algorithms (summing, averaging, filtering)
- Input/output and validation
- Object-oriented design principles

Good luck! Remember to test each method as you implement it.