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
        grid[0][0] = new Rook("black", new Position(0, 0));
        grid[0][1] = new Knight("black", new Position(0, 1));
        grid[0][2] = new Bishop("black", new Position(0, 2));
        grid[0][3] = new Queen("black", new Position(0, 3));
        grid[0][4] = new King("black", new Position(0, 4));
        grid[0][5] = new Bishop("black", new Position(0, 5));
        grid[0][6] = new Knight("black", new Position(0, 6));
        grid[0][7] = new Rook("black", new Position(0, 7));
        
        for (int i = 0; i < 8; i++) {
            grid[1][i] = new Pawn("black", new Position(1, i));
        }
    
        for (int i = 0; i < 8; i++) {
            grid[6][i] = new Pawn("white", new Position(6, i));
        }
    
        grid[7][0] = new Rook("white", new Position(7, 0));
        grid[7][1] = new Knight("white", new Position(7, 1));
        grid[7][2] = new Bishop("white", new Position(7, 2));
        grid[7][3] = new Queen("white", new Position(7, 3));
        grid[7][4] = new King("white", new Position(7, 4));
        grid[7][5] = new Bishop("white", new Position(7, 5));
        grid[7][6] = new Knight("white", new Position(7, 6));
        grid[7][7] = new Rook("white", new Position(7, 7));
    }

    public void movePiece(Position from, Position to) {
        Piece p = grid[from.getRow()][from.getCol()];
        grid[to.getRow()][to.getCol()] = p;
        grid[from.getRow()][from.getCol()] = null;
        if (p != null) p.setPosition(to);
    }

    public Piece getPiece(int row, int col) {
        if (row >= 0 && row < 8 && col >= 0 && col < 8) return grid[row][col];
        return null;
    }

    public Piece[][] getGrid() { return grid; }

    public boolean isCheck(String color) { return false; } 
    public boolean isCheckmate(String color) { return false; }

    public boolean kingExists(String color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p != null && p.getClass().getSimpleName().equalsIgnoreCase("King") 
                    && p.getColor().equalsIgnoreCase(color)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String[][] getSymbolGrid() {
        String[][] symbols = new String[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                symbols[r][c] = (grid[r][c] == null) ? "EMPTY" : grid[r][c].getSymbol();
            }
        }
        return symbols;
    }

    public void setPieceAt(int row, int col, Piece piece) {
        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            grid[row][col] = piece;
        }
    }

    public void clearGrid() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
            grid[r][c] = null;
            }
        }
    }
}