package solo_game.dataStructures;

/**
 * Data
 */
public class Data {

    public char col;
    public int row;
    public final static char START = 'A';

    public Data(int row, int column) {
        this.row = row;
        this.col = (char) (Data.START + column);
    }

}
