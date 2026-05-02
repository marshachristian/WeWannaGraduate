package pieces;

import utils.Position;
import board.Board;

public class Rook extends Piece {
    public Rook(String color, Position position) { super(color, position); }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wR" : "bR";
    }

    @Override
    public boolean isValidMove(Position to, Board board) {
        int rowDiff = to.getRow() - position.getRow();
        int colDiff = to.getCol() - position.getCol();
    
        if (rowDiff != 0 && colDiff != 0) return false;
    
        int rowStep = Integer.compare(rowDiff, 0);
        int colStep = Integer.compare(colDiff, 0);
        int currR = position.getRow() + rowStep;
        int currC = position.getCol() + colStep;
    
        while (currR != to.getRow() || currC != to.getCol()) {
            if (board.getPiece(currR, currC) != null) return false;
            currR += rowStep;
            currC += colStep;
        }
    
        Piece target = board.getPiece(to.getRow(), to.getCol());
        return target == null || !target.getColor().equals(this.color);
    }
}