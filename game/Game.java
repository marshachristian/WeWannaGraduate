public void play() {
    while (true) {
        board.display();
        System.out.println("Current turn: " + currentPlayer.getColor());
        String input = currentPlayer.getMoveInput();

        if (input.equalsIgnoreCase("exit")) break;

        try {
            String[] parts = input.trim().split("\\s+");
            if (parts.length != 2) throw new Exception("Please use format 'E2 E4'");

            Position from = Utils.parseNotation(parts[0]);
            Position to = Utils.parseNotation(parts[1]);

            if (from == null || to == null) throw new Exception("Invalid coordinates (A1-H8)");

            Piece p = board.getPiece(from);
            if (p == null) throw new Exception("No piece at starting position!");
            
            if (!p.getColor().equals(currentPlayer.getColor())) {
                throw new Exception("That is not your piece!");
            }

            board.movePiece(from, to);
            switchTurn();
            
        } catch (Exception e) {
            System.out.println(">>> ERROR: " + e.getMessage());
        }
    }
}