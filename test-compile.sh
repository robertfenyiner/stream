#!/bin/bash

# LatStream Test Compilation Script
echo "=== LatStream Minimal Test ==="

# Navigate to project
cd "/mnt/d/Onedrive Robert Personal/OneDrive/Documents/GitHub/stream"

echo "Cleaning previous build..."
mvn clean

echo "Attempting minimal compilation..."
mvn compile

if [ $? -eq 0 ]; then
    echo "✅ SUCCESS: Minimal compilation worked!"
    echo "Now let's try package..."
    mvn package -DskipTests
    
    if [ $? -eq 0 ]; then
        echo "✅ SUCCESS: Package creation worked!"
        echo "Ready to run: mvn spring-boot:run"
    else
        echo "❌ Package failed, but compile worked"
    fi
else
    echo "❌ COMPILATION FAILED"
    echo "Running with debug..."
    mvn compile -X
fi