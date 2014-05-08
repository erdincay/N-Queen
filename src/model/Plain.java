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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plain)) return false;

        Plain plain = (Plain) o;

        if (heuris != null ? !heuris.equals(plain.heuris) : plain.heuris != null) return false;
        if (queens != null ? !queens.equals(plain.queens) : plain.queens != null) return false;

        return true;
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
            if (mid == i){
                ret.append(lines[i] + " fitness = " + FitnessEval() + System.lineSeparator());
            }
            else {
                ret.append(lines[i] + System.lineSeparator());
            }
        }

        return ret.toString();
    }
}
