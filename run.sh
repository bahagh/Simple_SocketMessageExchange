#!/bin/bash

echo "Choose the mode of execution:"
echo "1. Both players in the same Java process"
echo "2. Each player in a separate Java process"
read -p "Enter your choice (1 or 2): " choice

case $choice in
    1)
        echo "Running both players in the same Java process..."
        java -cp target/classes com.bahaGmbh.projetjava.Main
        sleep 70
        ;;
    2)
        echo "Running each player in a separate Java process..."
        start bash -c "java -cp target/classes com.bahaGmbh.projetjava.Server; read -p 'Press enter to close this window...'"
        start bash -c "java -cp target/classes com.bahaGmbh.projetjava.SpielerClient; read -p 'Press enter to close this window...'"
        start bash -c "java -cp target/classes com.bahaGmbh.projetjava.SpielerClient; read -p 'Press enter to close this window...'"
        ;;
    *)
        echo "Invalid choice. Please choose either 1 or 2."
        ;;
esac
