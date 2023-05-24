import java.awt.*;

class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
    }

    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Piece[][] squares) {
        // Bishop can move diagonally
        int rowDiff = Math.abs(destRow - sourceRow);
        int colDiff = Math.abs(destCol - sourceCol);

        // Check if the move is along a diagonal
        if (rowDiff == colDiff) {
            int rowStep = destRow > sourceRow ? 1 : -1;
            int colStep = destCol > sourceCol ? 1 : -1;

            // Check if there are any obstructions along the diagonal path
            int currentRow = sourceRow + rowStep;
            int currentCol = sourceCol + colStep;

            while (currentRow != destRow && currentCol != destCol) {
                if (squares[currentRow][currentCol] != null) {
                    return false; // Occupied square along the path
                }
                currentRow += rowStep;
                currentCol += colStep;
            }

            // Check if the destination square is unoccupied or occupied by an opponent's piece
            Piece destinationPiece = squares[destRow][destCol];
            return destinationPiece == null || destinationPiece.getColor() != this.getColor();
        }

        return false;
    }

    public String getSymbol() {
        return "B";
    }
}