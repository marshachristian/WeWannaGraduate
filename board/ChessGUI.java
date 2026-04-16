package board;

import javax.swing.*;
import java.awt.*;

public class ChessGUI extends JFrame {
    private JPanel boardPanel;
    private JButton[][] squares = new JButton[8][8];

    public ChessGUI() {
        setTitle("Java Chess - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        boardPanel = new JPanel(new GridLayout(8, 8));
        
        initializeBoard();
        setupInitialPieces(); // newer method for adding symbols

        add(boardPanel);
        setVisible(true);
    }

    private void initializeBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JButton square = new JButton();
                if ((r + c) % 2 == 0) square.setBackground(new Color(235, 235, 208));
                else square.setBackground(new Color(119, 148, 85));
                
                square.setOpaque(true);
                square.setBorderPainted(false);
                square.setFont(new Font("SansSerif", Font.BOLD, 20)); // make sure symbols are readable

                squares[r][c] = square;
                boardPanel.add(square);
            }
        }
    }

    private void setupInitialPieces() {
        // symbols from Phase 1 requirements
        String[] blackPower = {"bR", "bN", "bB", "bQ", "bK", "bB", "bN", "bR"};
        String[] whitePower = {"wR", "wN", "wB", "wQ", "wK", "wB", "wN", "wR"};

        for (int i = 0; i < 8; i++) {
            // black pieces
            squares[0][i].setText(blackPower[i]);
            squares[0][i].setForeground(Color.BLACK);
            squares[1][i].setText("bp");
            squares[1][i].setForeground(Color.BLACK);

            // white pieces
            squares[6][i].setText("wp");
            squares[6][i].setForeground(new Color(0, 51, 102)); 
            squares[7][i].setText(whitePower[i]);
            squares[7][i].setForeground(new Color(0, 51, 102));
        }
    }
}
