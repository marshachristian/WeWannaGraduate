import game.Game;


public class Main {
    public static void main(String[] args) {
        System.out.println("--- Welcome to Java Console Chess ---");
        System.out.println("Type 'exit' at any time to quit.");
        
        // starting the game
        Game chessGame = new Game();
        chessGame.play();
    }
}