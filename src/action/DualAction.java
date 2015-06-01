package action;

import model.State;

import java.util.List;

/**
 * User: WHPM-1031
 * Date: 5/6/2014
 * Time: 2:13 AM
 */
public interface DualAction {
    List<State> act(State x, State y);
}
