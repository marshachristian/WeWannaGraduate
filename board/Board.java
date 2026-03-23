package board;

import pieces.*;
import utils.Position;

public class Board {
    private Piece[][] grid;

    public Board() {
        grid = new Piece[8][8];
        setupBoard();
    }

    private void setupBoard() {
        // initializing pawns
        for (int i = 0; i < 8; i++) {
            grid[1][i] = new Pawn("black", new Position(1, i));
            grid[6][i] = new Pawn("white", new Position(6, i));
        }
        // example of initializing rooks
        grid[0][0] = new Rook("black", new Position(0, 0));
        grid[0][7] = new Rook("black", new Position(0, 7));
        grid[7][0] = new Rook("white", new Position(7, 0));
        grid[7][7] = new Rook("white", new Position(7, 7));
        // adding other piece etc. 
    }

    public void display() {
        System.out.println("   A  B  C  D  E  F  G  H");
        for (int r = 0; r < 8; r++) {
            System.out.print((8 - r) + " ");
            for (int c = 0; c < 8; c++) {
                if (grid[r][c] == null) {
                    System.out.print("## ");
                } else {
                    System.out.print(grid[r][c].getSymbol() + " ");
                }
            }
            System.out.println(" " + (8 - r));
        }
        System.out.println("   A  B  C  D  E  F  G  H");
    }

    public void movePiece(Position from, Position to) {
        Piece p = grid[from.getRow()][from.getCol()];
        grid[to.getRow()][to.getCol()] = p;
        grid[from.getRow()][from.getCol()] = null;
    }
}