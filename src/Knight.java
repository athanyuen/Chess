import java.awt.*;

class Knight extends Piece {
    public Knight(Color color) {
        super(color);
    }

    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Piece[][] squares) {
        int rowDiff = Math.abs(destRow - sourceRow);
        int colDiff = Math.abs(destCol - sourceCol);

        // Knight can move in an L-shaped pattern: 2 squares horizontally and 1 square vertically, or 1 square horizontally and 2 squares vertically
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            // Check if the destination square is unoccupied or occupied by an opponent's piece
            Piece destinationPiece = squares[destRow][destCol];
            return destinationPiece == null || destinationPiece.getColor() != this.getColor();
        }

        return false;
    }

    public String getSymbol() {
        return "N";
    }
}