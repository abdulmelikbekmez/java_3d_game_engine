package solo_game.dataStructures;

import solo_game.ColoredBox;

import java.util.Objects;

/**
 * Data
 */
public class Data {

    public char col;
    public int row;
    public final static char START = 'A' - 1;
    public ColoredBox box;

    public Data(int row, int column, ColoredBox box) {
        this.row = row;
        this.col = (char) (START + column);
        this.box = box;
    }

    public boolean equal(int row, int col) {
        return (row == this.row) && ((char) (col + START) == this.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row, box);
    }
}
