package pieces;

import utils.Position;
import board.Board;

public class Bishop extends Piece {
    public Bishop(String color, Position position) { super(color, position); }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wp" : "bp";
    }

    @Override
    public boolean isValidMove(Position newPos, Board board) {
        
        return true; 
    }
}