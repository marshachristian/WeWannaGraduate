package utils;

public class Utils {
    /**
     * converts chess notation (e.g. "E2") to Position object (row, col)
     */
    public static Position parseNotation(String nota) {
        if (nota.length() < 2) return null;
        int col = Character.toUpperCase(nota.charAt(0)) - 'A';
        int row = 8 - Character.getNumericValue(nota.charAt(1));
        return new Position(row, col);
    }
}