package game;

import board.Board;
import java.util.Scanner;
import utils.Position;
import utils.Utils;

public class Game {
    private Board board;
    private Scanner scanner;

    public Game() {
        this.board = new Board();
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        while (true) {
            board.display();
            System.out.print("\nEnter move (e.g., E2 E4): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) break;

            String[] parts = input.split(" ");
            if (parts.length == 2) {
                Position from = Utils.parseNotation(parts[0]);
                Position to = Utils.parseNotation(parts[1]);
                
                if (from != null && to != null) {
                    board.movePiece(from, to);
                } else {
                    System.out.println("Invalid Coordinates!");
                }
            }
        }
    }
}