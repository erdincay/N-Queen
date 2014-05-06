package model;

import eval.Heuristics;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 3:21 PM
 */
public class Plain implements Node{
    private State queens;
    private Heuristics heuris;

    public Plain(int size, Heuristics heuris) {
        this.queens = new Queens(size);
        this.heuris = heuris;
    }

    public Plain(State queens, Heuristics heuris) {
        this.queens = queens;
        this.heuris = heuris;
    }

    @Override
    public int FitnessEval() {
        return heuris.eval(queens);
    }

    @Override
    public State getState() {
        return queens;
    }

    @Override
    public Heuristics getHeuristic() {
        return heuris;
    }

    @Override
    public int compareTo(Object o) {
        int myCost = FitnessEval();
        int othCost = ((Plain)o).FitnessEval();

        return myCost - othCost;
    }
}
