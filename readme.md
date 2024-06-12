
# Messaging Game

This Java application allows two players to exchange messages in a fun and interactive way. Whether you want to run both players within the same Java process or each player in a separate process, this project has got you covered!

## Table of Contents

- [Introduction](#introduction)
- [Project Structure](#project-structure)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [How to Run](#how-to-run)
  - [Running the Game](#running-the-game)
  - [Execution Modes](#execution-modes)
- [Additional Notes](#additional-notes)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This Game is a multiplayer Java application where two players engage in a conversation by exchanging messages. The game offers two modes of execution: 

1. **Both players in the same Java process**: Players interact directly within the same Java process.
2. **Each player in a separate Java process**: Players communicate through a central server, each running in its own Java process.



## Project Structure

The project adheres to the Maven directory structure and includes the following components:

project-root/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bahaGmbh/
│   │   │           └── projetjava/
│   │   │               ├── Main.java
│   │   │               ├── Player.java
│   │   │               ├── Server.java
│   │   │               └── SpielerClient.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/
│               └── bahaGmbh/
│                   └── projetjava/
│                      └── PlayerTest.java.java
├
├
├── pom.xml
├── run.sh
└── README.md

The project adheres to the Maven directory structure and includes the following components:

- `src/main/java/com/bahaGmbh/projetjava/`: Contains Java source files.
- `src/main/resources/`: Includes any additional resources required.
- `src/test/java/com/bahaGmbh/projetjava/`: Houses tests .
- `pom.xml`: Maven project configuration file.
- `run.sh`: Shell script to run the game with different execution modes.
- `README.md`: Documentation file you're currently reading.


## Features

- Simple and intuitive messaging interface.
- Flexible execution modes to suit different preferences.
- Error handling and logging for improved reliability.
- Maven-based project structure for easy dependency management and build automation.

## Prerequisites

Before running the Messaging Game, ensure you have the following installed on your system:

- Java Development Kit (JDK) version 8 or higher
- Apache Maven

## How to Run

Follow these steps to run the Messaging Game:

### Running the Game


1. Navigate to the project directory in your terminal.
2. Run the following command:

    ```
    sh run.sh
    ```

4. Follow the on-screen prompts to choose your desired execution mode.

### Execution Modes

- **Mode 1: Both Players in the Same Java Process**
  - Players interact directly within the same Java process.
  - The `Main` class initializes the game, and players exchange messages.
  
- **Mode 2: Each Player in a Separate Java Process**
  - Players communicate through a central server, while each one of them running in its own Java process.
  - The `Server` class manages communication between players, while the `SpielerClient` class represents each player.

## Additional Notes

- Players take turns sending messages until both have sent and received 10 messages.




