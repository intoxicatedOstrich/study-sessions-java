#!/bin/bash

# Build script for Study Session Tracker
# This script downloads dependencies, compiles the code, and provides run commands

set -e

echo "🔧 Setting up Study Session Tracker..."

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
SQLITE_JAR="sqlite-jdbc-3.44.1.0.jar"
SQLITE_URL="https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar"
SRC_DIR="src"
BUILD_DIR="build"

# Function to print colored output
print_status() {
    echo -e "${BLUE}📋 $1${NC}"
}

print_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

# Check if Java is installed
check_java() {
    print_status "Checking Java installation..."
    
    if command -v java &> /dev/null && command -v javac &> /dev/null; then
        JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
        print_success "Java found: $JAVA_VERSION"
        
        # Check if Java 17+
        MAJOR_VERSION=$(echo $JAVA_VERSION | cut -d. -f1)
        if [ "$MAJOR_VERSION" -ge 17 ]; then
            print_success "Java version is compatible (17+)"
        else
            print_warning "Java 17+ recommended. Current version: $JAVA_VERSION"
        fi
    else
        print_error "Java not found! Please install Java 17+ and try again."
        exit 1
    fi
}

# Download SQLite JDBC driver if not present
download_dependencies() {
    print_status "Checking dependencies..."
    
    if [ ! -f "$SQLITE_JAR" ]; then
        print_status "Downloading SQLite JDBC driver..."
        curl -o "$SQLITE_JAR" "$SQLITE_URL"
        print_success "SQLite JDBC driver downloaded"
    else
        print_success "SQLite JDBC driver already present"
    fi
}

# Compile the Java source files
compile_code() {
    print_status "Compiling Java source files..."
    
    # Create build directory
    mkdir -p "$BUILD_DIR"
    
    # Compile all Java files
    javac -cp ".:$SQLITE_JAR:slf4j-api-1.7.36.jar:slf4j-nop-1.7.36.jar" -d "$BUILD_DIR" $SRC_DIR/*.java
    
    print_success "Compilation successful!"
}

# Clean build artifacts
clean() {
    print_status "Cleaning build artifacts..."
    rm -rf "$BUILD_DIR"
    rm -f *.class
    print_success "Clean complete"
}

# Run the application
run() {
    print_status "Starting Study Session Tracker..."
    
    # Check if running in interactive mode
    if [ -t 0 ]; then
        # Interactive mode - stdin is a terminal
        echo ""
        java -cp "$BUILD_DIR:$SQLITE_JAR:slf4j-api-1.7.36.jar:slf4j-nop-1.7.36.jar" StudyTrackerApp
    else
        # Non-interactive mode
        print_warning "Running in non-interactive mode. For full functionality, run directly in terminal."
        echo ""
        java -cp "$BUILD_DIR:$SQLITE_JAR:slf4j-api-1.7.36.jar:slf4j-nop-1.7.36.jar" StudyTrackerApp
    fi
}

# Run tests (placeholder for future implementation)
test() {
    print_warning "Tests not implemented yet"
    print_status "You can add JUnit tests in src/test/ directory"
}

# Show usage information
usage() {
    echo "Study Session Tracker Build Script"
    echo ""
    echo "Usage: $0 [command]"
    echo ""
    echo "Commands:"
    echo "  build    - Download dependencies and compile code"
    echo "  run      - Run the application"
    echo "  clean    - Clean build artifacts"
    echo "  test     - Run tests (not implemented yet)"
    echo "  help     - Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 build    # Build the project"
    echo "  $0 run      # Run the application"
    echo "  $0 clean    # Clean and rebuild: $0 clean && $0 build"
}

# Main script logic
case "${1:-build}" in
    "build")
        check_java
        download_dependencies
        compile_code
        print_success "Build complete! Run with: $0 run"
        ;;
    "run")
        if [ ! -d "$BUILD_DIR" ]; then
            print_warning "No build found. Building first..."
            check_java
            download_dependencies
            compile_code
        fi
        run
        ;;
    "clean")
        clean
        ;;
    "test")
        test
        ;;
    "help"|"-h"|"--help")
        usage
        ;;
    *)
        print_error "Unknown command: $1"
        echo ""
        usage
        exit 1
        ;;
esac
