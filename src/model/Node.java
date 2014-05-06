package model;

import eval.Heuristics;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 11:00 PM
 */
public interface Node extends Comparable {
    public int CostEval();

    public State getState();

    public Heuristics getHeuristic();
}
