package board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessGUI extends JFrame {
    private JPanel boardPanel;
    private JButton[][] squares = new JButton[8][8];
    
    // to keep track of the currently selected square for moving pieces
    private JButton selectedSquare = null;

    public ChessGUI() {
        setTitle("Java Chess - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        boardPanel = new JPanel(new GridLayout(8, 8));
        createGrid();
        setupInitialPieces();

        add(boardPanel);
        setVisible(true);
    }

    private void createGrid() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton square = new JButton();
                
                if ((row + col) % 2 == 0) {
                    square.setBackground(new Color(235, 235, 208));
                } else {
                    square.setBackground(new Color(119, 148, 85));
                }
                
                square.setOpaque(true);
                square.setBorderPainted(false);
                square.setFont(new Font("Arial", Font.BOLD, 22));

                // adding action listener
                square.addActionListener(new ButtonClickListener());

                squares[row][col] = square;
                boardPanel.add(square);
            }
        }
    }

    /**
     * defines the behavior when a square is clicked:
     */
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedSquare = (JButton) e.getSource();

            // first click
            if (selectedSquare == null) {
                if (!clickedSquare.getText().equals("")) {
                    selectedSquare = clickedSquare;
                    selectedSquare.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }
            } 
            // second click
            else {
                // if the same square is clicked again, deselect it
                if (clickedSquare.equals(selectedSquare)) {
                    selectedSquare.setBorder(null);
                    selectedSquare = null;
                } 
                else {
                    // move the piece to the new square
                    clickedSquare.setText(selectedSquare.getText());
                    clickedSquare.setForeground(selectedSquare.getForeground());
                    
                    // clear the original square
                    selectedSquare.setText("");
                    selectedSquare.setBorder(null);
                    
                    // reset selection
                    selectedSquare = null;
                }
            }
            // force the GUI to update after each click
            boardPanel.revalidate();
            boardPanel.repaint();
        }
    }

    private void setupInitialPieces() {
        String[] blackPower = {"bR", "bN", "bB", "bQ", "bK", "bB", "bN", "bR"};
        String[] whitePower = {"wR", "wN", "wB", "wQ", "wK", "wB", "wN", "wR"};

        for (int i = 0; i < 8; i++) {
            squares[0][i].setText(blackPower[i]);
            squares[0][i].setForeground(Color.BLACK);
            squares[1][i].setText("bp");
            squares[1][i].setForeground(Color.BLACK);

            squares[6][i].setText("wp");
            squares[6][i].setForeground(new Color(0, 51, 102));
            squares[7][i].setText(whitePower[i]);
            squares[7][i].setForeground(new Color(0, 51, 102));
        }
    }
}
