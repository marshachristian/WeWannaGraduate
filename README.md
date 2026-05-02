# WeWannaGraduate
Team Members: Marsha Christian   
Class: CS3354.255  

Java Chess - Phase 3 (Final Integration)
📌 Project Overview
This project is a fully functional, GUI-based Chess application developed in Java. Phase 3 marks the final integration between the Graphical User Interface and the Backend Game Logic. The application follows Object-Oriented Programming (OOP) principles, specifically utilizing Polymorphism and Encapsulation to enforce chess rules and manage game state.

🚀 How to Run the Game
Follow these commands in your terminal from the root directory (Phase3/) to compile and launch the application.

1. Compile the Source Code
This command compiles all packages (board, pieces, utils) and places the class files in the current directory:

```bash
javac -d . board/*.java pieces/*.java utils/*.java
```

2. Launch the Application
Run the ChessGUI class (or Main if you are using a separate entry point):

```bash
java board.ChessGUI
```
🛠️ Phase 3 Integration Details
According to the rubric requirements, this phase successfully integrates the following components:

Polymorphic Move Validation: The ChessGUI triggers move validation by calling the abstract isValidMove method defined in the Piece class. Each specific piece (Pawn, Rook, Knight, etc.) provides its own unique logic implementation.

Encapsulated Game State: The Board class acts as the "Source of Truth." The GUI never manipulates piece data directly; it sends requests to the Board, which then manages the Piece objects.

Event Handling: Implementation of ActionListener within ButtonClickListener allows the GUI to respond to user clicks, validate moves, update the display, and log game history.

🎮 Implemented Features
Move History: A side panel that logs every successful move made during the game.

Undo System: A functional "Undo Move" button that utilizes a Stack to revert the board to its previous state.

Save/Load: Ability to persist the current game state to a text file and resume it later.

Turn Management: Automatic switching between White and Black turns with visual indicators.

Rule Enforcement: Basic move validation for all pieces, preventing illegal movements or friendly fire.