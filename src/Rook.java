import java.awt.*;

class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }


    // right now the rook moves in all direction but it doesn't take opponent pieces
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Piece[][] squares) {

        if(squares[destRow][destCol] != null && squares[destRow][destCol].getColor() == this.getColor()){
            return false;
        }
        if (sourceRow == destRow) {
            // Check for obstructions along the horizontal path
            if(sourceCol < destCol){
                for (int col = sourceCol + 1; col < destCol; col++) {
                    if (squares[sourceRow][col] != null) {
                        return false; // Obstruction found
                    }
                }
            }
            else if(destCol < sourceCol){
                for(int col = sourceCol - 1; col > destCol; col--){
                    if (squares[sourceRow][col] != null) {
                        return false; // Obstruction found
                    }
                }
            }
            else if(destCol == sourceCol){
                return false;
            }

            return true; // Valid horizontal move
        } else if (sourceCol == destCol) {
            // Check for obstructions along the vertical path
            if(sourceRow < destRow){
                for (int row = sourceRow + 1; row < destRow; row++) {
                    if (squares[row][sourceCol] != null) {
                        return false; // Obstruction found
                    }
                }
            }
            else if(destRow < sourceRow){
                for (int row = sourceRow - 1; row > destRow; row++) {
                    if (squares[row][sourceCol] != null) {
                        return false; // Obstruction found
                    }
                }
            }

            return true; // Valid vertical move
        }

        return false; // Invalid move
    }

    public String getSymbol() {
        return "R";
    }
}