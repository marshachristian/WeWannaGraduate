package pieces;

import utils.Position;
import board.Board;

public abstract class Piece {
    protected String color; // "white" or "black"
    protected Position position;

    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    public String getColor() { return color; }
    public abstract String getSymbol(); 
    
    public abstract boolean isValidMove(Position newPos, Board board);
}