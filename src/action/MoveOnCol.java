package action;

import model.Queens;
import model.State;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 7:47 PM
 */
public class MoveOnCol implements Action{
    private int QueenIndex;
    private int step;

    public MoveOnCol(int queenIndex, int step) {
        QueenIndex = queenIndex;
        this.step = step;
    }

    @Override
    public State act(State cur) {
        if (cur ==  null ) {
            return cur;
        }

        Queens queens = (Queens) cur;

        if (QueenIndex < 0 || QueenIndex >= queens.size()){
            return cur;
        }

        final Queens newQs = new Queens(queens);
        newQs.get(QueenIndex).MoveOnCol(step);

        return newQs;
    }
}
