package pieces;

import utils.Position;
import board.Board;

public class Bishop extends Piece {
    public Bishop(String color, Position position) { super(color, position); }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wB" : "bB";
    }

    @Override
    public boolean isValidMove(Position to, Board board) {
        int rowDiff = Math.abs(to.getRow() - position.getRow());
        int colDiff = Math.abs(to.getCol() - position.getCol());
    
        if (rowDiff != colDiff || rowDiff == 0) return false;
    
        int rowStep = (to.getRow() > position.getRow()) ? 1 : -1;
        int colStep = (to.getCol() > position.getCol()) ? 1 : -1;
        int currR = position.getRow() + rowStep;
        int currC = position.getCol() + colStep;
    
        while (currR != to.getRow()) {
            if (board.getPiece(currR, currC) != null) return false;
            currR += rowStep;
            currC += colStep;
        }
    
        Piece target = board.getPiece(to.getRow(), to.getCol());
        return target == null || !target.getColor().equals(this.color);
    }
}