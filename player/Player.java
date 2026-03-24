package player;

import java.util.Scanner;

/**
 * player class represents a chess player, either "white" or "black".
 */
public class Player {
    private String color; 
    private Scanner scanner;

    /**
     * @param color the color of the player ("white" or "black").
     */
    public Player(String color) {
        this.color = color;
        this.scanner = new Scanner(System.in);
    }

    public String getColor() {
        return color;
    }

    /**
     * asking player to enter their move in the format "E2 E4".
     * @return the move input as a string.
     */
    public String getMoveInput() {
        System.out.print(color.toUpperCase() + "'s turn. Enter move (e.g., E2 E4): ");
        return scanner.nextLine();
    }
}