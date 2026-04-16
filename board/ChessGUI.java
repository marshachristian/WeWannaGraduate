package board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for Phase 2.
 * features: 8x8 grid, piece placement, click-to-move, turn enforcement, and win pop-ups.
 */
public class ChessGUI extends JFrame {
    private JPanel boardPanel;
    private JButton[][] squares = new JButton[8][8];
    private JButton selectedSquare = null;
    private String currentTurn = "white"; 
    private JLabel turnLabel;

    public ChessGUI() {
        setTitle("Java Chess - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 750);
        setLocationRelativeTo(null);

        // uses BorderLayout to place the turn label at the bottom
        setLayout(new BorderLayout());

        boardPanel = new JPanel(new GridLayout(8, 8));
        createGrid();
        setupInitialPieces();

        add(boardPanel, BorderLayout.CENTER);
        
        // turn indicator
        turnLabel = new JLabel("Current Turn: WHITE", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        turnLabel.setPreferredSize(new Dimension(700, 50));
        add(turnLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * creates the 8x8 checkerboard grid.
     */
    private void createGrid() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton square = new JButton();
                
                if ((row + col) % 2 == 0) {
                    square.setBackground(new Color(235, 235, 208)); // Light
                } else {
                    square.setBackground(new Color(119, 148, 85));  // Dark
                }
                
                square.setOpaque(true);
                square.setBorderPainted(false);
                square.setFont(new Font("Arial", Font.BOLD, 22));

                square.addActionListener(new ButtonClickListener());

                squares[row][col] = square;
                boardPanel.add(square);
            }
        }
    }

    /**
     * movement logic and turn switching.
     */
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedSquare = (JButton) e.getSource();

            // step 1: selecting a piece
            if (selectedSquare == null) {
                String text = clickedSquare.getText();
                // check to make sure it's not empty and belongs to the current player
                if (!text.equals("") && text.startsWith(currentTurn.substring(0, 1))) {
                    selectedSquare = clickedSquare;
                    selectedSquare.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }
            } 
            // step 2: moving a piece
            else {
                // deselects if clicking the same square
                if (clickedSquare.equals(selectedSquare)) {
                    selectedSquare.setBorder(null);
                    selectedSquare = null;
                } 
                else {
                    String targetText = clickedSquare.getText();
                    
                    // avoid friendly fire (capture own piece) - switches selection instead
                    if (!targetText.equals("") && targetText.startsWith(currentTurn.substring(0, 1))) {
                        selectedSquare.setBorder(null);
                        selectedSquare = clickedSquare;
                        selectedSquare.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                        return; 
                    }

                    // check for endgame (King capture)
                    if (targetText.equals("bK") || targetText.equals("wK")) {
                        JOptionPane.showMessageDialog(null, currentTurn.toUpperCase() + " WINS!");
                        System.exit(0);
                    }

                    // execute move
                    clickedSquare.setText(selectedSquare.getText());
                    clickedSquare.setForeground(selectedSquare.getForeground());
                    selectedSquare.setText("");
                    selectedSquare.setBorder(null);
                    selectedSquare = null;

                    // update turn state and UI label
                    currentTurn = currentTurn.equals("white") ? "black" : "white";
                    turnLabel.setText("Current Turn: " + currentTurn.toUpperCase());
                }
            }
            boardPanel.revalidate();
            boardPanel.repaint();
        }
    }

    private void setupInitialPieces() {
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