# Study Session Tracker 

A Java project for tracking study sessions with a command-line interface.

##  What You'll Build

A simple study tracker that lets you:
- Track when and what you study
- Save your sessions to a file
- Calculate statistics like total study time and averages
- Track your study streak (consecutive days)

##  Quick Start

### Prerequisites
- Java 17 or higher installed
- Basic command line knowledge

### Build and Run
```bash
# Build the project
./build.sh

# Run the application
./build.sh run

# Run tests
./build.sh test

# Clean build files
./build.sh clean
```

## 📁 Project Structure

```
study-sessions-java/
├── src/
│   ├── main/java/com/studytracker/
│   │   ├── StudySession.java       # Core data model
│   │   ├── StudyTracker.java       # Main logic class
│   │   ├── SimpleFileStorage.java  # File save/load
│   │   └── StudyTrackerCLI.java    # Command-line interface
│   └── test/java/com/studytracker/
│       ├── StudySessionTest.java
│       ├── StudyTrackerTest.java
│       └── SimpleFileStorageTest.java
├── lib/                            # External libraries (JUnit)
├── build.sh                        # Build script
└── README.md                       # This file
```

##  Sample Usage

```
=== Study Session Tracker ===

1. Start new study session
2. End current session
3. View all sessions
4. View sessions by subject
5. Calculate total study time
6. Calculate average session length
7. View study streak
8. Save sessions to file
9. Load sessions from file
0. Exit

Enter your choice: 1

Enter subject: Math
Study session started for Math at 2024-01-15T14:30

Enter your choice: 2

Enter difficulty (1-5): 4
Enter notes (optional): Chapter 5 review
Study session ended! Duration: 45 minutes

Enter your choice: 7

Current study streak: 3 consecutive days! 🔥
```

## 🧪 Testing

The project includes comprehensive unit tests for all classes:

```bash
# Run all tests
./build.sh test

# Tests cover:
# - StudySession creation and validation
# - StudyTracker operations (add, search, statistics)
# - File storage (save/load with error handling)
```

## 💡 Implementation Tips

### For `StudySession.java`
- Use private fields with public getters/setters
- Validate that end time is after start time
- Calculate duration in minutes
- Override `toString()` for better display

### For `StudyTracker.java`
- Use `ArrayList<StudySession>` to store sessions
- Implement methods one at a time and test each
- Think about edge cases (empty list, null values)

### For `SimpleFileStorage.java`
- Handle file not found gracefully
- Use try-with-resources for automatic file closing
- Test with malformed data files

## Common Issues and Solutions

### "Cannot find symbol" errors
- Make sure all imports are correct
- Check that class names match file names
- Verify method signatures match between classes

### File not found when loading
- The program creates files in the current directory
- Use absolute paths if needed
- Check file permissions

### Tests failing
- Read error messages carefully
- The tests expect specific behavior
- Check edge cases (null, empty lists, invalid dates)

## Ways to extend this project:
- Adding a GUI with JavaFX or Swing
- Using a database instead of files (SQLite, H2)
- Creating a REST API for the tracker
- Building a web interface
- Adding data visualization charts
