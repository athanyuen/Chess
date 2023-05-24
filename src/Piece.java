import java.awt.*;

abstract class Piece {
    private Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public abstract boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Piece[][] squares);

    public abstract String getSymbol();

    protected Color getColor() {
        return color;
    }
}
