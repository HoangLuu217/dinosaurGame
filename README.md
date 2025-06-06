# Dinosaur Game

Welcome to the Dinosaur Game, a simple yet engaging game inspired by the Google Chrome obstacle game. In this game, you control a dinosaur that must jump over obstacles such as cacti and birds while navigating through a desert landscape.

## Project Structure

The project is organized as follows:

```
dinosaur-game
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── game
│   │   │   │   ├── Game.java
│   │   │   │   ├── GameWindow.java
│   │   │   │   └── GamePanel.java
│   │   │   ├── entities
│   │   │   │   ├── Dinosaur.java
│   │   │   │   ├── Obstacle.java
│   │   │   │   ├── Bird.java
│   │   │   │   ├── Cactus.java
│   │   │   │   └── Cloud.java
│   │   │   └── utils
│   │   │       ├── Constants.java
│   │   │       └── CollisionDetector.java
│   ├── test
│   │   └── java
│   │       └── game
│   │           └── GameTest.java
├── pom.xml
└── README.md
```

## Setup Instructions

1. **Clone the repository**: 
   ```
   git clone <repository-url>
   ```

2. **Navigate to the project directory**:
   ```
   cd dinosaur-game
   ```

3. **Build the project**:
   ```
   mvn clean install
   ```

4. **Run the game**:
   ```
   mvn exec:java -Dexec.mainClass="game.Game"
   ```

## Game Rules

- Use the spacebar to make the dinosaur jump.
- Avoid obstacles like cacti and birds to keep the game going.
- The game ends when the dinosaur collides with an obstacle.

Enjoy playing the Dinosaur Game!#   d i n o s a u r G a m e  
 