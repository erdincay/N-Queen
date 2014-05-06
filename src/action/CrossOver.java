package action;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by WHPM-1031 on 5/6/2014.
 */
public class CrossOver implements DualAction {
    @Override
    public List<State> act(State x, State y) {
        Queens parentX = (Queens) x;
        Queens parentY = (Queens) y;
        int size = parentX.size();
        if (size != parentY.size()) {
            return null;
        }

        Random generator = new Random();
        int crossIndex = generator.nextInt(size - 1);

        Queens childX = new Queens(0);
        Queens childY = new Queens(0);
        for (int i = 0; i < size; i++) {
            if (i <= crossIndex) {
                childX.add(new Queen(parentX.get(i)));
                childY.add(new Queen(parentY.get(i)));
            } else {
                childX.add(new Queen(parentY.get(i)));
                childY.add(new Queen(parentX.get(i)));
            }
        }

        List<State> ret = new ArrayList<State>(2);

        return ret;
    }
}
