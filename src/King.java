import java.awt.*;

class King extends Piece {
    public King(Color color) {
        super(color);
    }

    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Piece[][] squares) {
        int rowDiff = Math.abs(destRow - sourceRow);
        int colDiff = Math.abs(destCol - sourceCol);

        // Check if the move is within one square in any direction
        if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1) || (rowDiff == 1 && colDiff == 1)) {
            // Check if the destination square is unoccupied or occupied by an opponent's piece
            Piece destinationPiece = squares[destRow][destCol];
            return destinationPiece == null || destinationPiece.getColor() != this.getColor();
        }

        return false;
    }

    public String getSymbol() {
        return "K";
    }
}