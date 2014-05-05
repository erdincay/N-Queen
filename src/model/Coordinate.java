package model;

/**
 * User: Ding
 * Date: 4/17/2014
 * Time: 2:56 PM
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate oth) {
        this.x = oth.x;
        this.y = oth.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean sameLine(Coordinate oth) {
        return y == oth.y;
    }

    public boolean sameRow(Coordinate oth) {
        return x == oth.x;
    }

    public boolean sameOblique(Coordinate oth) {
        return Math.abs(x - oth.x) == Math.abs(y - oth.y);
    }
}
