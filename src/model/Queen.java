package model;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 3:19 PM
 */
public class Queen {
    Coordinate coord;

    public Queen(Coordinate coord) {
        this.coord = coord;
    }

    public Queen(Queen oth){
        coord = new Coordinate(oth.coord);
    }

    public Coordinate getCoord() {
        return coord;
    }

    public boolean Conflict(Queen oth) {
        return coord.sameRow(oth.coord) || coord.sameCol(oth.coord) || coord.sameDiagonal(oth.coord);
    }

    public void MoveOnRow(int step) {
        coord = new Coordinate(coord.getX() + step, coord.getY());
    }

    public void MoveOnCol(int step) {
        coord = new Coordinate(coord.getX(), coord.getY() + step);
    }

    public void MoveOnDiagonal(int step) {
        coord = new Coordinate(coord.getX() + step, coord.getY() + step);
    }
}
