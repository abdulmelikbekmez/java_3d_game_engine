package solo_game.dataStructures;

import solo_game.ColoredBox;

/**
 * Data
 */
public class Data {

    public int x;
    public int y;
    public ColoredBox box;

    public Data(int x, int y, ColoredBox box) {
        this.y = y;
        this.x = x;
        this.box = box;
    }

    public boolean equal(int x, int y) {
        return (y == this.y) && (x == this.x);
    }

}
