package model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 3:21 PM
 */
public class Plain {
    private int plainSize;
    private List<Queen> queens;

    public Plain(int size) {
        this.plainSize = size;
        queens = new ArrayList<Queen>(plainSize);
    }

    public int ConflictQueens() {
        int count = 0;
        for (int i = 0; i < queens.size(); i++) {
            Queen curQ = queens.get(i);
            for (int j = i + 1; j < queens.size(); j++) {
                if (curQ.Conflict(queens.get(j))) {
                    count++;
                }
            }
        }
        return count;
    }
}
