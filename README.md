# WeWannaGraduate
Team Members: Marsha Christian   
Class: CS3354.255  

# Console-Based Chess Game (Java)

A functional, text-based chess application developed for Phase One. This game allows two players to interact via the command line using standard chess notation.

## Features
* **Full 8x8 Board Setup:** All 32 pieces initialized in standard starting positions.
* **Turn-Based Logic:** Alternates between White and Black players.
* **Robust Input Parsing:** Handles user input (e.g., "E2 E4") and provides error feedback for invalid moves or incorrect formats without crashing.
* **Modular Architecture:** Organized into logical packages (pieces, board, game, utils) for easy maintainability.

## How to Run
1. Ensure you have **JDK 11** or higher installed.
2. Open your terminal in the project root directory.
3. To ensure the GUI and all packages (board, pieces, utils) are compiled correctly, please run the following commands from the root directory:
   **Compile:**

   ```bash
   javac Main.java board/*.java pieces/*.java utils/*.java

   Then

   ```bash
   java Main

Enjoy!
