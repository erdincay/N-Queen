package model;

import eval.Heuristics;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 11:00 PM
 */
public interface Node extends Comparable {
    int FitnessEval();
    State getState();
    boolean ReachGoal();
    Heuristics getHeuristic();

    @Override
    int compareTo(Object o);
}
