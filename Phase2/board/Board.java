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
        // setup for black pieces (top of board)
        grid[0][0] = new Rook("black", new Position(0, 0));
        grid[0][1] = new Knight("black", new Position(0, 1));
        grid[0][2] = new Bishop("black", new Position(0, 2));
        grid[0][3] = new Queen("black", new Position(0, 3));
        grid[0][4] = new King("black", new Position(0, 4));
        grid[0][5] = new Bishop("black", new Position(0, 5));
        grid[0][6] = new Knight("black", new Position(0, 6));
        grid[0][7] = new Rook("black", new Position(0, 7));
        for (int i = 0; i < 8; i++) grid[1][i] = new Pawn("black", new Position(1, i));
    
        // setup for white pieces (bottom of board)
        grid[7][0] = new Rook("white", new Position(7, 0));
        grid[7][1] = new Knight("white", new Position(7, 1));
        grid[7][2] = new Bishop("white", new Position(7, 2));
        grid[7][3] = new Queen("white", new Position(7, 3));
        grid[7][4] = new King("white", new Position(7, 4));
        grid[7][5] = new Bishop("white", new Position(7, 5));
        grid[7][6] = new Knight("white", new Position(7, 6));
        grid[7][7] = new Rook("white", new Position(7, 7));
        for (int i = 0; i < 8; i++) grid[6][i] = new Pawn("white", new Position(6, i));
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