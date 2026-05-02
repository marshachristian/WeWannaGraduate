package pieces;

import utils.Position;
import board.Board;

public class Pawn extends Piece {
    public Pawn(String color, Position position) { super(color, position); }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wp" : "bp";
    }

    @Override
    public boolean isValidMove(Position to, Board board) {
        int direction = color.equals("white") ? -1 : 1;
        int startRow = color.equals("white") ? 6 : 1;
        int rowDiff = to.getRow() - position.getRow();
        int colDiff = Math.abs(to.getCol() - position.getCol());
        Piece target = board.getPiece(to.getRow(), to.getCol());
    
        if (colDiff == 0 && rowDiff == direction && target == null) return true;
    
        if (colDiff == 0 && position.getRow() == startRow && rowDiff == 2 * direction) {
            if (target == null && board.getPiece(position.getRow() + direction, position.getCol()) == null) {
                return true;
            }
        }
    
        if (colDiff == 1 && rowDiff == direction && target != null) {
            return !target.getColor().equals(this.color);
        }
    
        return false;
    }
}