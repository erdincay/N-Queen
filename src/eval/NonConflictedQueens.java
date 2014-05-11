package eval;

import model.Queens;
import model.State;

/**
 * User: Ding
 * Date: 5/10/2014
 * Time: 1:08 AM
 */
public class NonConflictedQueens implements Heuristics {
    @Override
    public int eval(State cur) {
        return ((Queens)cur).CountConflict(false);
    }

    @Override
    public boolean PreferSmall() {
        return false;
    }
}
