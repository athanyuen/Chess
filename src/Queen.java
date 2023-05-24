import java.awt.*;

class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Piece[][] squares) {
        // Queen can move horizontally, vertically, and diagonally
        int rowDiff = Math.abs(destRow - sourceRow);
        int colDiff = Math.abs(destCol - sourceCol);

        // Check if the move is along a horizontal, vertical, or diagonal line
        if (sourceRow == destRow || sourceCol == destCol || rowDiff == colDiff) {
            // Check if there are any obstructions along the path
            if (sourceRow == destRow) {
                int colStep = destCol > sourceCol ? 1 : -1;
                int currentCol = sourceCol + colStep;

                while (currentCol != destCol) {
                    if (squares[sourceRow][currentCol] != null) {
                        return false; // Occupied square along the path
                    }
                    currentCol += colStep;
                }
            } else if (sourceCol == destCol) {
                int rowStep = destRow > sourceRow ? 1 : -1;
                int currentRow = sourceRow + rowStep;

                while (currentRow != destRow) {
                    if (squares[currentRow][sourceCol] != null) {
                        return false; // Occupied square along the path
                    }
                    currentRow += rowStep;
                }
            } else {
                int rowStep = destRow > sourceRow ? 1 : -1;
                int colStep = destCol > sourceCol ? 1 : -1;
                int currentRow = sourceRow + rowStep;
                int currentCol = sourceCol + colStep;

                while (currentRow != destRow && currentCol != destCol) {
                    if (squares[currentRow][currentCol] != null) {
                        return false; // Occupied square along the path
                    }
                    currentRow += rowStep;
                    currentCol += colStep;
                }
            }

            // Check if the destination square is unoccupied or occupied by an opponent's piece
            Piece destinationPiece = squares[destRow][destCol];
            return destinationPiece == null || destinationPiece.getColor() != this.getColor();
        }

        return false;
    }

    public String getSymbol() {
        return "Q";
    }
}