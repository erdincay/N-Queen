package eval;

import model.Queens;
import model.State;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 3:41 PM
 */
public class ConflictedQueens implements Heuristics{
    @Override
    public int eval(State cur) {
        return ((Queens)cur).CountConflict(true);
    }

    @Override
    public boolean PreferSmall() {
        return true;
    }
}
