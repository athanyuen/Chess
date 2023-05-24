import java.awt.*;

class Board {
    private Piece[][] squares;

    public Board() {
        squares = new Piece[8][8];
    }

    public void initialize() {
        // Initialize the chess board with pieces
        // Add white pieces
        squares[0][0] = new Rook(Color.WHITE);
        squares[0][1] = new Knight(Color.WHITE);
        squares[0][2] = new Bishop(Color.WHITE);
        squares[0][3] = new Queen(Color.WHITE);
        squares[0][4] = new King(Color.WHITE);
        squares[0][5] = new Bishop(Color.WHITE);
        squares[0][6] = new Knight(Color.WHITE);
        squares[0][7] = new Rook(Color.WHITE);
        for (int i = 0; i < 8; i++) {
            squares[1][i] = new Pawn(Color.WHITE);
        }

        // Add black pieces
        squares[7][0] = new Rook(Color.BLACK);
        squares[7][1] = new Knight(Color.BLACK);
        squares[7][2] = new Bishop(Color.BLACK);
        squares[7][3] = new Queen(Color.BLACK);
        squares[7][4] = new King(Color.BLACK);
        squares[7][5] = new Bishop(Color.BLACK);
        squares[7][6] = new Knight(Color.BLACK);
        squares[7][7] = new Rook(Color.BLACK);
        for (int i = 0; i < 8; i++) {
            squares[6][i] = new Pawn(Color.BLACK);
        }
    }

    public void display() {
        // Display the current state of the board
        System.out.println(ANSI_RESET + "  a b c d e f g h");
        for (int i = 7; i >= 0; i--) {
            System.out.print(ANSI_RESET + (i + 1 + " "));
            for (int j = 0; j < 8; j++) {
                Piece piece = squares[i][j];
                if (piece == null) {
                    System.out.print(ANSI_RESET + "- ");
                } else {
                    if(piece.getColor() == Color.WHITE){
                        System.out.print(ANSI_WHITE + piece.getSymbol() + " ");
                    }
                    else{
                        System.out.print(ANSI_BLACK + piece.getSymbol() + " ");
                    }

                }
            }
            System.out.println();
        }
    }

    public boolean movePiece(String source, String destination) {
        // Move the piece from source to destination
        int sourceRow = Character.getNumericValue(source.charAt(1) - 1);
        int sourceCol = source.charAt(0) - 97;
        int destRow = Character.getNumericValue(destination.charAt(1) - 1);
        int destCol = destination.charAt(0) - 97;

        if(sourceRow > 8 || sourceRow < 1 || sourceCol > 8 || sourceCol < 1 || destRow > 8 || destRow < 1 || destCol > 8 || destCol < 1){
            return false; // Out of bounds
        }
        Piece piece = squares[sourceRow][sourceCol];
        if (piece == null) {
            return false; // No piece at the source position
        }
        else if (!piece.isValidMove(sourceRow, sourceCol, destRow, destCol, squares)) {
            return false; // Invalid move for the piece
        }
        else return piece.isValidMove(sourceRow, sourceCol, destRow, destCol, squares);
    }
    public void move(String source, String destination){
        int sourceRow = Character.getNumericValue(source.charAt(1) - 1);
        int sourceCol = source.charAt(0) - 97;
        int destRow = Character.getNumericValue(destination.charAt(1) - 1);
        int destCol = destination.charAt(0) - 97;
        Piece piece = squares[sourceRow][sourceCol];
        squares[sourceRow][sourceCol] = null;
        squares[destRow][destCol] = piece;
    }

    public boolean isCheckmate() {
        // Check if the game is in checkmate

        // Find the king's position
        int kingRow = -1;
        int kingCol = -1;
        Color currentPlayerColor = ChessGame.moveCount % 2 == 0 ? Color.WHITE : Color.BLACK;

        // Find the king's position
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && piece instanceof King && piece.getColor() == currentPlayerColor) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
            if (kingRow != -1) {
                break;
            }
        }

        // Check if the king is under attack
        if (!isKingUnderAttack(kingRow, kingCol, currentPlayerColor)) {
            return false; // King is not under attack, not in checkmate
        }

        // Check if any move can remove the check
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && piece.getColor() == currentPlayerColor) {
                    for (int destRow = 0; destRow < 8; destRow++) {
                        for (int destCol = 0; destCol < 8; destCol++) {
                            if (piece.isValidMove(row, col, destRow, destCol, squares) && movePiece(row, col, destRow, destCol)) {
                                boolean kingStillUnderAttack = isKingUnderAttack(kingRow, kingCol, currentPlayerColor);
                                undoMove(row, col, destRow, destCol); // Undo the move
                                if (!kingStillUnderAttack) {
                                    return false; // There is a move that removes the check
                                }
                            }
                        }
                    }
                }
            }
        }

        return true; // No move can remove the check, in checkmate
    }

    private boolean movePiece(int sourceRow, int sourceCol, int destRow, int destCol) {
        Piece sourcePiece = squares[sourceRow][sourceCol];
        Piece destPiece = squares[destRow][destCol];

        if (sourcePiece == null || !sourcePiece.isValidMove(sourceRow, sourceCol, destRow, destCol, squares)) {
            return false; // Invalid move
        }

        // Perform the move
        squares[destRow][destCol] = sourcePiece;
        squares[sourceRow][sourceCol] = null;

        return true;
    }

    private void undoMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        Piece movedPiece = squares[destRow][destCol];
        squares[destRow][destCol] = null;
        squares[sourceRow][sourceCol] = movedPiece;
    }

    public boolean isKingUnderAttack(int kingRow, int kingCol, Color kingColor){
        // Check if the king is under attack by any opponent piece

        // Check for attacks from knights
        int[] knightRows = {-2, -1, 1, 2, 2, 1, -1, -2};
        int[] knightCols = {1, 2, 2, 1, -1, -2, -2, -1};

        for (int i = 0; i < 8; i++) {
            int targetRow = kingRow + knightRows[i];
            int targetCol = kingCol + knightCols[i];
            if (isValidPosition(targetRow, targetCol) && squares[targetRow][targetCol] instanceof Knight &&
                    squares[targetRow][targetCol].getColor() != kingColor) {
                return true;
            }
        }

        // Check for attacks from bishops and queens (diagonal attacks)
        int[] diagonalRows = {-1, -1, 1, 1};
        int[] diagonalCols = {1, -1, 1, -1};

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 8; j++) {
                int targetRow = kingRow + j * diagonalRows[i];
                int targetCol = kingCol + j * diagonalCols[i];
                if (!isValidPosition(targetRow, targetCol)) {
                    break;
                }
                Piece piece = squares[targetRow][targetCol];
                if (piece != null) {
                    if ((piece instanceof Bishop || piece instanceof Queen) && piece.getColor() != kingColor) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }

        // Check for attacks from rooks and queens (horizontal and vertical attacks)
        int[] straightRows = {-1, 0, 1, 0};
        int[] straightCols = {0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 8; j++) {
                int targetRow = kingRow + j * straightRows[i];
                int targetCol = kingCol + j * straightCols[i];
                if (!isValidPosition(targetRow, targetCol)) {
                    break;
                }
                Piece piece = squares[targetRow][targetCol];
                if (piece != null) {
                    if ((piece instanceof Rook || piece instanceof Queen) && piece.getColor() != kingColor) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }

        // Check for attacks from pawns
        int pawnDirection = kingColor == Color.WHITE ? 1 : -1;
        int[] pawnAttackCols = {-1, 1};

        for (int i = 0; i < 2; i++) {
            int targetRow = kingRow + pawnDirection;
            int targetCol = kingCol + pawnAttackCols[i];
            if (isValidPosition(targetRow, targetCol) && squares[targetRow][targetCol] instanceof Pawn &&
                    squares[targetRow][targetCol].getColor() != kingColor) {
                return true;
            }
        }

        // Check for attacks from the opponent king
        int[] kingRows = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] kingCols = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int targetRow = kingRow + kingRows[i];
            int targetCol = kingCol + kingCols[i];
            if (isValidPosition(targetRow, targetCol) && squares[targetRow][targetCol] instanceof King &&
                    squares[targetRow][targetCol].getColor() != kingColor) {
                return true;
            }
        }

        return false;
    }

    private boolean isValidPosition(int row, int col) {
        // Check if the given row and column are valid positions on the board
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public boolean isStalemate() {
        // Check if the game is in stalemate

        // Find the current player's color
        Color currentPlayerColor = ChessGame.moveCount % 2 == 0 ? Color.WHITE : Color.BLACK;

        // Check if the current player has any valid moves
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && piece.getColor() == currentPlayerColor) {
                    for (int destRow = 0; destRow < 8; destRow++) {
                        for (int destCol = 0; destCol < 8; destCol++) {
                            if (piece.isValidMove(row, col, destRow, destCol, squares) && movePiece(row, col, destRow, destCol)) {
                                movePiece(destRow, destCol, row, col); // Undo the move
                                return false; // Player has a valid move, not in stalemate
                            }
                        }
                    }
                }
            }
        }

        return true; // No valid move for the current player, in stalemate
    }

    public static final String ANSI_WHITE = "\u001b[37m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RESET = "\u001B[0m";

}