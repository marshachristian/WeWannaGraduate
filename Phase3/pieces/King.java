package pieces;

import utils.Position;
import board.Board;

public class King extends Piece {
    public King(String color, Position position) { super(color, position); }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wK" : "bK";
    }

    @Override
    public boolean isValidMove(Position to, Board board) {
        int rowDiff = Math.abs(to.getRow() - position.getRow());
        int colDiff = Math.abs(to.getCol() - position.getCol());
    
        if (rowDiff > 1 || colDiff > 1 || (rowDiff == 0 && colDiff == 0)) return false;
    
        Piece target = board.getPiece(to.getRow(), to.getCol());
        return target == null || !target.getColor().equals(this.color);
    }
}