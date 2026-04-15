package game;

import board.Board;
import player.Player;
import pieces.Piece;
import utils.Position;
import utils.Utils;

public class Game {
    // game class manages the overall state of the chess game, including the board and players
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentPlayer;

    // constructor initializes the game state, including the board and players
    public Game() {
        this.board = new Board();
        this.whitePlayer = new Player("white");
        this.blackPlayer = new Player("black");
        this.currentPlayer = whitePlayer;
    }

    public void play() {
        while (true) {
            board.display();
            System.out.println("\n--- Current turn: " + currentPlayer.getColor().toUpperCase() + " ---");
            String input = currentPlayer.getMoveInput();

            if (input.equalsIgnoreCase("exit")) break;

            try {
                String[] parts = input.trim().split("\\s+");
                if (parts.length != 2) throw new Exception("Please use format 'E2 E4'");

                Position from = Utils.parseNotation(parts[0]);
                Position to = Utils.parseNotation(parts[1]);

                if (from == null || to == null) throw new Exception("Invalid coordinates (A1-H8)");

                Piece p = board.getPiece(from);
                if (p == null) throw new Exception("No piece at starting position!");
                
                if (!p.getColor().equals(currentPlayer.getColor())) {
                    throw new Exception("That is not your piece!");
                }

                board.movePiece(from, to);
                switchTurn();
                
            } catch (Exception e) {
                System.out.println(">>> ERROR: " + e.getMessage());
            }
        }
    }

    // this method switches the current player after a successful move
    private void switchTurn() {
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }
}