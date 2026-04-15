package board;

import javax.swing.*;
import java.awt.*;

/**
 * creates a GUI for the chess game using Java Swing. it sets up an 8x8 grid of buttons 
 * to represent the chessboard squares, with alternating colors for a traditional chessboard look.
 *  each button can later be used to display pieces and handle user interactions for moving pieces.
 */

public class ChessGUI extends JFrame {
    private JPanel boardPanel;
    private JButton[][] squares = new JButton[8][8];

    public ChessGUI() {
        setTitle("Java Chess - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        // 8x8 grid layout for the chessboard
        boardPanel = new JPanel(new GridLayout(8, 8));
        
        initializeBoard();

        add(boardPanel);
        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton square = new JButton();
                
                // alternate colors for the chessboard squares
                if ((row + col) % 2 == 0) {
                    square.setBackground(new Color(235, 235, 208)); // Light
                } else {
                    square.setBackground(new Color(119, 148, 85));  // Dark
                }
                
                // ensure buttons are opaque and have no border for a cleaner look
                square.setOpaque(true);
                square.setBorderPainted(false);

                squares[row][col] = square;
                boardPanel.add(square);
            }
        }
    }
}
