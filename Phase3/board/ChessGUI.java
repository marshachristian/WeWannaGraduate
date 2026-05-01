package board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class ChessGUI extends JFrame {
    private JPanel boardPanel;
    private JButton[][] squares = new JButton[8][8];
    private JButton selectedSquare = null;
    private String currentTurn = "white"; 
    private JLabel turnLabel;
    private Board gameBoard; 
    
    // --- GUI feature 2: board color themes ---
    private Color darkSquareColor = new Color(119, 148, 85); 
    private JTextArea historyArea;
    private Stack<String[][]> boardHistory = new Stack<>(); // for undo

    public ChessGUI() {
        gameBoard = new Board(); 
        setTitle("Java Chess - Phase 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 800); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        createGrid();
        updateBoardUI(); 
        setVisible(true);

        // --- GUI feature 1: game menu ---
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newItem = new JMenuItem("New Game");
        JMenuItem saveItem = new JMenuItem("Save Game");
        JMenuItem loadItem = new JMenuItem("Load Game");
        JMenuItem settingsItem = new JMenuItem("Settings");
        newItem.addActionListener(e -> resetGame());
	    saveItem.addActionListener(e -> saveGame());
        loadItem.addActionListener(e -> loadGame());
        settingsItem.addActionListener(e -> openSettings());
        gameMenu.add(newItem);
        gameMenu.add(saveItem);
        gameMenu.add(loadItem);
        gameMenu.add(settingsItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        // --- GUI feature 3: history panel and undo button ---
        JPanel sidePanel = new JPanel(new BorderLayout());
        historyArea = new JTextArea(20, 20);
        historyArea.setEditable(false);
        historyArea.setText("Move History:\n-----------------\n");
        JScrollPane scrollPane = new JScrollPane(historyArea);
        
        JButton undoButton = new JButton("Undo Move");
        undoButton.addActionListener(e -> undoMove());
        
        sidePanel.add(scrollPane, BorderLayout.CENTER);
        sidePanel.add(undoButton, BorderLayout.SOUTH);
        add(sidePanel, BorderLayout.EAST);

        boardPanel = new JPanel(new GridLayout(8, 8));
        createGrid();
        updateBoardUI();

        add(boardPanel, BorderLayout.CENTER);
        
        turnLabel = new JLabel("Current Turn: WHITE", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        turnLabel.setPreferredSize(new Dimension(700, 50));
        add(turnLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void createGrid() {
        boardPanel.removeAll();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton square = new JButton();
                if ((row + col) % 2 == 0) square.setBackground(new Color(235, 235, 208));
                else square.setBackground(darkSquareColor);
                
                square.setOpaque(true);
                square.setBorderPainted(false);
                square.setFont(new Font("Arial", Font.BOLD, 22));
                square.addActionListener(new ButtonClickListener());
                squares[row][col] = square;
                boardPanel.add(square);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void resetGame() {
        currentTurn = "white";
        turnLabel.setText("Current Turn: WHITE");
        selectedSquare = null;
        historyArea.setText("Move History:\n-----------------\n");
        boardHistory.clear();
        createGrid();
        updateBoardUI();
    }

    private void openSettings() {
        String[] options = {"Classic Green", "Modern Gray"};
        int choice = JOptionPane.showOptionDialog(this, "Choose Board Style:", "Settings",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        if (choice == 0) darkSquareColor = new Color(119, 148, 85);
        else if (choice == 1) darkSquareColor = Color.GRAY;
        
        createGrid();
        updateBoardUI();
    }

    private void saveGame() {
        try (java.io.PrintWriter out = new java.io.PrintWriter("chess_save.txt")) {
            out.println(currentTurn); 
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    String text = squares[r][c].getText();
                    out.println(text.equals("") ? "EMPTY" : text);
                }
            }
            historyArea.append("SYSTEM: Game saved to chess_save.txt\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Save Failed!");
        }
    }
    
    private void loadGame() {
        try (java.util.Scanner in = new java.util.Scanner(new java.io.File("chess_save.txt"))) {
            currentTurn = in.nextLine();
            turnLabel.setText("Current Turn: " + currentTurn.toUpperCase());
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    String val = in.nextLine();
                    squares[r][c].setText(val.equals("EMPTY") ? "" : val);
                    if (val.startsWith("w")) squares[r][c].setForeground(new Color(0, 51, 102));
                    else squares[r][c].setForeground(Color.BLACK);
                }
            }
            historyArea.append("SYSTEM: Game loaded successfully!\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No save file found!");
        }
    }

    private void undoMove() {
        if (!boardHistory.isEmpty()) {
            String[][] previousState = boardHistory.pop();
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    squares[r][c].setText(previousState[r][c]);
                    if (previousState[r][c].startsWith("w")) squares[r][c].setForeground(new Color(0, 51, 102));
                    else squares[r][c].setForeground(Color.BLACK);
                }
            }
            currentTurn = currentTurn.equals("white") ? "black" : "white";
            turnLabel.setText("Current Turn: " + currentTurn.toUpperCase());
            historyArea.append("UNDO: Reverted move.\n");
        }
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedSquare = (JButton) e.getSource();
            int row = getRow(clickedSquare);
            int col = getCol(clickedSquare);

            if (selectedSquare == null) {
                Piece p = gameBoard.getPiece(row, col);
                if (p != null && p.getColor().equalsIgnoreCase(currentTurn)) {
                    selectedSquare = clickedSquare;
                    selectedSquare.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }
            } else {
                int startRow = getRow(selectedSquare);
                int startCol = getCol(selectedSquare);
                Position from = new Position(startRow, startCol);
                Position to = new Position(row, col);
                Piece p = gameBoard.getPiece(startRow, startCol);

                if (p != null && p.isValidMove(to, gameBoard.getGrid())) {
                    gameBoard.movePiece(from, to);
                    
                    // switch turn only on successful move
                    currentTurn = currentTurn.equals("white") ? "black" : "white";
                    turnLabel.setText("Current Turn: " + currentTurn.toUpperCase());
                    
                    updateBoardUI(); 
                    checkEndgame(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Illegal Move!");
                }
                selectedSquare.setBorder(null);
                selectedSquare = null;
            }
        }
    }

    private void updateBoardUI() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = gameBoard.getPiece(r, c);
                squares[r][c].setText(p == null ? "" : p.getSymbol());
                if (p != null) {
                    squares[r][c].setForeground(p.getColor().equals("white") ? new Color(0, 51, 102) : Color.BLACK);
                }
            }
        }
    }

    private int getRow(JButton b) {
        for(int r=0; r<8; r++) for(int c=0; c<8; c++) if(squares[r][c]==b) return r;
        return -1;
    }
    private int getCol(JButton b) {
        for(int r=0; r<8; r++) for(int c=0; c<8; c++) if(squares[r][c]==b) return c;
        return -1;
    }

    private void checkEndgame() {
        if (gameBoard.isCheckmate(currentTurn)) {
            JOptionPane.showMessageDialog(this, "CHECKMATE! Game Over.");
            resetGame();
        } else if (gameBoard.isCheck(currentTurn)) {
            turnLabel.setText("Current Turn: " + currentTurn.toUpperCase() + " (IN CHECK!)");
        }
    }
}

