package pieces;

import utils.Position;
import board.Board;

public class Knight extends Piece {
    public Knight(String color, Position position) { super(color, position); }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wN" : "bN";
    }

    @Override
    public boolean isValidMove(Position to, Board board) {
        int rowDiff = Math.abs(to.getRow() - position.getRow());
        int colDiff = Math.abs(to.getCol() - position.getCol());
    
        boolean isLType = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        if (!isLType) return false;
    
        Piece target = board.getPiece(to.getRow(), to.getCol());
        return target == null || !target.getColor().equals(this.color);
    }
}