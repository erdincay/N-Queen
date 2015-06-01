package action;

import model.State;

public interface Action {
    State act(State cur);
}
