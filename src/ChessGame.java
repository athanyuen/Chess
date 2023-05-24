import java.util.Scanner;

public class ChessGame {
    public static int moveCount = 0;
    public static void main(String[] args) {
        Board board = new Board();
        board.initialize();
        // Game loop
        boolean gameOver = false;
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            board.display();
            // Check for game over conditions
            if (board.isCheckmate()) {
                gameOver = true;
                System.out.println(ANSI_RED + "Checkmate! Game over.");
                System.exit(0);
            } else if (board.isStalemate()) {
                gameOver = true;
                System.out.println(ANSI_RED + "Stalemate! Game over.");
                System.exit(0);
            }
            if(moveCount % 2 == 0){
                System.out.println(ANSI_BLUE + "White to move. Enter source and destination coordinates (e.g., 'a2 a4'): ");
            }
            else if (moveCount % 2 != 0){
                System.out.println(ANSI_BLUE + "Black to move. Enter source and destination coordinates (e.g., 'a2 a4'): ");
            }
            String input = scanner.nextLine();
            // Parse user input
            String[] coordinates = input.split(" ");
            if (coordinates.length != 2 || coordinates[0].length() != 2 || coordinates[1].length() != 2 )  {
                System.out.println(ANSI_RED + "Invalid input. Please try again.");
                continue;
            }
            String source = coordinates[0];
            String destination = coordinates[1];
            // Move the piece
            boolean moveSuccessful = board.movePiece(source, destination);
            if (!moveSuccessful) {
                System.out.println(ANSI_RED + "Invalid move. Please try again.");
                continue;
            }
            else if(moveSuccessful){
                board.move(source, destination);
                moveCount++;
                continue;
            }
        }
    }
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

}