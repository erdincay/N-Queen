package action;

import model.State;

import java.util.List;

/**
 * Created by WHPM-1031 on 5/6/2014.
 */
public interface DualAction {
    public List<State> act(State x, State y);
}
