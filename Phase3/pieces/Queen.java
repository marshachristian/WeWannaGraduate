package pieces;

import utils.Position;
import board.Board;

public class Queen extends Piece {
    public Queen(String color, Position position) { super(color, position); }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wQ" : "bQ";
    }

@Override
public boolean isValidMove(Position to, Board board) {
    int rowDiff = Math.abs(to.getRow() - position.getRow());
    int colDiff = Math.abs(to.getCol() - position.getCol());

    boolean movesLikeRook = (to.getRow() == position.getRow() || to.getCol() == position.getCol());
    boolean movesLikeBishop = (rowDiff == colDiff);

    if (!movesLikeRook && !movesLikeBishop) return false;

    int rowStep = Integer.compare(to.getRow() - position.getRow(), 0);
    int colStep = Integer.compare(to.getCol() - position.getCol(), 0);
    
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