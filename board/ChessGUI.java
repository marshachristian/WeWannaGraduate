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
    
    // --- GUI feature 2: board color themes ---
    private Color darkSquareColor = new Color(119, 148, 85); 
    private JTextArea historyArea;
    private Stack<String[][]> boardHistory = new Stack<>(); // for undo

    public ChessGUI() {
        setTitle("Java Chess - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 800); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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
        setupInitialPieces();

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
        setupInitialPieces();
    }

    private void openSettings() {
        String[] options = {"Classic Green", "Modern Gray"};
        int choice = JOptionPane.showOptionDialog(this, "Choose Board Style:", "Settings",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        if (choice == 0) darkSquareColor = new Color(119, 148, 85);
        else if (choice == 1) darkSquareColor = Color.GRAY;
        
        createGrid();
        setupInitialPieces();
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
            if (selectedSquare == null) {
                String text = clickedSquare.getText();
                if (!text.equals("") && text.startsWith(currentTurn.substring(0, 1))) {
                    selectedSquare = clickedSquare;
                    selectedSquare.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }
            } else {
                if (clickedSquare.equals(selectedSquare)) {
                    selectedSquare.setBorder(null);
                    selectedSquare = null;
                } else {
                    String targetText = clickedSquare.getText();
                    if (!targetText.equals("") && targetText.startsWith(currentTurn.substring(0, 1))) {
                        selectedSquare.setBorder(null);
                        selectedSquare = clickedSquare;
                        selectedSquare.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                        return; 
                    }

                    if (!targetText.equals("")) {
                        historyArea.append("CAPTURE: " + currentTurn.toUpperCase() + " took " + targetText + "!\n");
                    } else {
                        historyArea.append(currentTurn.toUpperCase() + ": " + selectedSquare.getText() + " moved.\n");
                    }

                    // saves state before move for undo functionality
                    String[][] stateToSave = new String[8][8];
                    for(int r=0; r<8; r++) for(int c=0; c<8; c++) stateToSave[r][c] = squares[r][c].getText();
                    boardHistory.push(stateToSave);

                    if (targetText.equals("bK") || targetText.equals("wK")) {
                        JOptionPane.showMessageDialog(null, currentTurn.toUpperCase() + " WINS!");
                        resetGame();
                        return;
                    }

                    clickedSquare.setText(selectedSquare.getText());
                    clickedSquare.setForeground(selectedSquare.getForeground());
                    selectedSquare.setText("");
                    selectedSquare.setBorder(null);
                    selectedSquare = null;
                    
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