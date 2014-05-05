package model;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 3:19 PM
 */
public class Queen {
    Coordinate coord;

    public boolean Conflict(Queen oth) {
        return coord.sameLine(oth.coord) || coord.sameRow(oth.coord) || coord.sameOblique(oth.coord);
    }

    public Queen(Coordinate coord) {
        this.coord = coord;
    }

    public void Move() {

    }
}
