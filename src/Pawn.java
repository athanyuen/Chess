import java.awt.*;

class Pawn extends Piece {
    private boolean hasMoved;
    public Pawn(Color color) {
        super(color);

    }

    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Piece[][] squares) {
        int rowDiff = Math.abs(destRow - sourceRow);
        int colDiff = Math.abs(destCol - sourceCol);

        if (getColor() == Color.WHITE) {
            if (sourceCol == destCol && destRow - sourceRow == 1 && squares[destRow][destCol] == null) {
                return true; // Move one square forward
            } else if (sourceCol == destCol && sourceRow == 1 && destRow - sourceRow == 2 && squares[destRow][destCol] == null && squares[destRow - 1][destCol] == null) {
                return true; // Move two squares forward from the initial position
            } else if (colDiff == 1 && destRow - sourceRow == 1 && squares[destRow][destCol] != null && squares[destRow][destCol].getColor() == Color.BLACK) {
                return true; // Capture an opponent's piece diagonally
            }
        } else {
            if (sourceCol == destCol && sourceRow - destRow == 1 && squares[destRow][destCol] == null) {
                return true; // Move one square forward
            } else if (sourceCol == destCol && sourceRow == 6 && sourceRow - destRow == 2 && squares[destRow][destCol] == null && squares[destRow + 1][destCol] == null) {
                return true; // Move two squares forward from the initial position
            } else if (colDiff == 1 && sourceRow - destRow == 1 && squares[destRow][destCol] != null && squares[destRow][destCol].getColor() == Color.WHITE) {
                return true; // Capture an opponent's piece diagonally
            }
        }

        return false;
    }

    public String getSymbol() {
        return "P";
    }
}