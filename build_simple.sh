#!/bin/bash

# Simple build script for Study Tracker

# Create build directory
mkdir -p build

# Compile all Java files
echo "Compiling Java files..."
javac -d build src/main/java/com/studytracker/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    echo ""
    echo "To run the program:"
    echo "  java -cp build com.studytracker.Main"
else
    echo "✗ Compilation failed. Please fix the errors above."
    exit 1
fi