package eval;

import model.State;

public interface Heuristics {
    public int eval(State cur);
    public boolean PreferSmall();
}
