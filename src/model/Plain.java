package model;

import eval.Heuristics;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 3:21 PM
 */
public class Plain implements Node {
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

    private int GoalState() {
        if (heuris.PreferSmall()) {
            return 0;
        }

        int size = ((Queens) queens).size() - 1;
        return (1 + size) * size / 2;
    }

    @Override
    public int FitnessEval() {
        return heuris.eval(queens);
    }

    public boolean ReachGoal() {
        return FitnessEval() == GoalState();
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
        int othCost = ((Plain) o).FitnessEval();

        if (heuris.PreferSmall()) {
            return myCost - othCost;
        }

        return othCost - myCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plain)) return false;

        Plain plain = (Plain) o;

        return !(heuris != null ? !heuris.equals(plain.heuris) : plain.heuris != null) && !(queens != null ? !queens.equals(plain.queens) : plain.queens != null);

    }

    @Override
    public int hashCode() {
        int result = queens != null ? queens.hashCode() : 0;
        result = 31 * result + (heuris != null ? heuris.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String[] lines = queens.toString().split(System.lineSeparator());

        int mid = lines.length / 2;

        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (mid == i) {
                ret.append(lines[i]).append(" fitness = ").append(FitnessEval()).append(System.lineSeparator());
            } else {
                ret.append(lines[i]).append(System.lineSeparator());
            }
        }

        return ret.toString();
    }
}
