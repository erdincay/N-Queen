package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 9:55 PM
 */
public class Queens extends ArrayList<Queen> implements State {
    public Queens(int size) {
        Random generator = new Random();
        for (int i = 0; i < size; i++) {
            Coordinate coord = new Coordinate(i, generator.nextInt(size));
            this.add(new Queen(coord));
        }
    }

    public Queens(Collection<? extends Queen> c) {
        for (Queen q : c) {
            this.add(new Queen(q));
        }
    }

    public int CountConflict() {
        int count = 0;
        for (int i = 0; i < this.size(); i++) {
            Queen curQ = this.get(i);
            for (int j = i + 1; j < this.size(); j++) {
                if (curQ.Conflict(this.get(j))) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean Contain(Coordinate coord) {
        for (Queen q : this) {
            if (q.getCoord().equals(coord)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(this.size() * this.size() * 3);
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                String place = "* ";
                if (Contain(new Coordinate(j, this.size() - 1 - i))) {
                    place = "Q ";
                }
                ret.append(place);
            }
            ret.append(System.lineSeparator());
        }

        return ret.toString();
    }
}
