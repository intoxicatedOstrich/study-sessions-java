# Study Session Tracker - Java OOP Implementation

A comprehensive Java application demonstrating solid Object-Oriented Programming principles through a practical study session tracking system.

## 🎯 Project Overview

This application helps students track their study sessions, set goals, and visualize their progress over time. It's designed as an educational project to demonstrate:

- **Clean Architecture** with layered design
- **SOLID Principles** in action
- **Design Patterns** (Repository, Service Layer, Dependency Injection)
- **Database Integration** with SQLite
- **Comprehensive Error Handling**

## 🏗️ Architecture

```
StudyTrackerApp (Main)
    ↓
StudyTrackerCLI (Presentation Layer)
    ↓
StudySessionService (Business Logic Layer)
    ↓
StudySessionRepository (Data Access Layer)
    ↓
SQLite Database
```

### Key Components

- **Models**: `StudySession`, `StudyGoal`, `StudyProgressReport`
- **Repository**: `StudySessionRepository` (interface) & `SQLiteStudySessionRepository` (implementation)
- **Service**: `StudySessionService` (business logic and validation)
- **CLI**: `StudyTrackerCLI` (user interface)
- **Main**: `StudyTrackerApp` (application entry point)

## 🚀 Features

- ✅ Create, read, update, and delete study sessions
- ✅ Set daily/weekly study goals
- ✅ Generate progress reports with ASCII charts
- ✅ Tag sessions by subject and difficulty
- ✅ Search sessions by subject
- ✅ Calculate study streaks
- ✅ View statistics by subject
- ✅ Comprehensive error handling

## 📋 Requirements

- **Java 17+** (for text blocks and modern language features)
- **SQLite JDBC Driver**: `sqlite-jdbc-3.44.1.0.jar`

## 🔧 Setup and Installation

### 1. Download Dependencies

Download the SQLite JDBC driver:
```bash
curl -O https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar
```

### 2. Compile the Project

```bash
# Compile all Java files
javac -cp ".:sqlite-jdbc-3.44.1.0.jar" src/*.java

# Move compiled classes to current directory (optional)
mv src/*.class .
```

### 3. Run the Application

```bash
java -cp ".:sqlite-jdbc-3.44.1.0.jar" StudyTrackerApp
```

## 💡 Key OOP Concepts Demonstrated

### Encapsulation
- All model fields are private with controlled access through getters/setters
- Validation in setters prevents invalid state
- Methods like `getTags()` return copies to prevent external modification

### Abstraction
- `StudySessionRepository` interface hides implementation details
- Service layer abstracts complex business operations
- Users interact with simple, meaningful methods

### Polymorphism
- Repository interface allows swapping implementations (SQLite ↔ PostgreSQL)
- Different difficulty levels handled through enums
- Future extensibility built in

### Composition
- Service class "has-a" repository (dependency injection)
- CLI "has-a" service
- Objects composed of other objects rather than inheritance

## 🎯 Design Patterns Used

### Repository Pattern
`StudySessionRepository` separates data access from business logic. This means you could swap SQLite for PostgreSQL without changing any other code.

### Service Layer Pattern
`StudySessionService` contains all business rules. Want to change what makes a valid study session? Change it in one place.

### Dependency Injection
The main class wires everything together. This makes testing easier and reduces coupling between classes.

## 📊 Sample Usage

```
📚 Welcome to Study Session Tracker! 📚
=====================================

========================================
📋 MAIN MENU
========================================
1. 📝 Create New Study Session
2. 📖 View All Sessions
3. 🔍 Search Sessions by Subject
4. 📊 Generate Progress Report
5. 📈 View Statistics
6. 🗑️  Delete Session
7. 🚪 Exit
========================================
Choose an option (1-7): 1

📝 CREATE NEW STUDY SESSION
------------------------------
Subject: Java Programming
Duration (minutes): 90
Difficulty Level:
1. Easy
2. Medium
3. Hard
4. Very Hard
Choose difficulty (1-4): 3
✅ Study session created successfully!
📋 StudySession{id=1, subject='Java Programming', duration=90 mins, difficulty=HARD, time=2024-06-14 15:30}
```

## 🔍 Code Structure Explanation

### Model Layer (`StudySession.java`, `StudyGoal.java`)
- **Purpose**: Represents the core data and business entities
- **Key Features**: Encapsulation, validation, business logic methods
- **Example**: `StudySession.getDurationHours()` converts minutes to hours

### Repository Layer (`StudySessionRepository.java`, `SQLiteStudySessionRepository.java`)
- **Purpose**: Handles all data persistence operations
- **Key Features**: Interface segregation, SQL operations, data mapping
- **Example**: `findBySubject()` enables subject-based searches

### Service Layer (`StudySessionService.java`)
- **Purpose**: Contains business logic and validation rules
- **Key Features**: Business rule enforcement, complex operations
- **Example**: Enforces minimum 5-minute and maximum 8-hour session limits

### Presentation Layer (`StudyTrackerCLI.java`)
- **Purpose**: Handles user interaction and input/output
- **Key Features**: Menu-driven interface, input validation, error handling
- **Example**: Provides user-friendly error messages and confirmation dialogs

## 🧪 Testing Strategy

The layered architecture makes testing straightforward:

1. **Unit Tests**: Test individual classes in isolation
2. **Integration Tests**: Test repository + database interactions
3. **Service Tests**: Test business logic with mocked repositories
4. **End-to-End Tests**: Test complete user workflows

## 🔧 Extension Ideas

- **Add Goals Management**: Implement goal CRUD operations
- **Export Data**: Add CSV/JSON export functionality
- **Study Groups**: Add collaborative study features
- **Notifications**: Implement study reminders
- **Web Interface**: Replace CLI with web UI
- **Analytics**: Add more sophisticated progress tracking

## 📚 Learning Outcomes

After working with this code, you'll understand:

- How to structure larger Java applications
- When and why to use interfaces vs. concrete classes
- How dependency injection reduces coupling
- Why separation of concerns matters
- How to design for testability and maintainability

## 🤝 Contributing

This is an educational project! Feel free to:
- Add new features
- Improve the existing code
- Add more comprehensive error handling
- Create unit tests
- Optimize database queries

## 📖 References

- **Clean Code** by Robert C. Martin
- **Design Patterns** by Gang of Four
- **Effective Java** by Joshua Bloch
- **Spring Framework Documentation** (for real-world dependency injection)

---

*Remember: The goal isn't just to make it work, but to understand WHY it's structured this way. Each design decision makes the code more maintainable, testable, and extensible.*
