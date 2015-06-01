package eval;

import model.State;

public interface Heuristics {
    int eval(State cur);
    boolean PreferSmall();

    @Override
    String toString();
}
