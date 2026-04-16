import board.ChessGUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // program start
        System.out.println("--- Starting Chess GUI ---");
        
        SwingUtilities.invokeLater(() -> {
            new ChessGUI();
        });
        
        // removed "Type 'exit' to quit" since the GUI will handle closing the application
    }
}