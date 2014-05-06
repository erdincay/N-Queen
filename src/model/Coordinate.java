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

    public boolean sameRow(Coordinate oth) {
        return y == oth.y;
    }

    public boolean sameCol(Coordinate oth) {
        return x == oth.x;
    }

    public boolean sameDiagonal(Coordinate oth) {
        return Math.abs(x - oth.x) == Math.abs(y - oth.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;

        Coordinate that = (Coordinate) o;

        return x == that.x && y == that.y;

    }
}
