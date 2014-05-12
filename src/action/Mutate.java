package action;

import model.Coordinate;
import model.Queen;
import model.Queens;
import model.State;

import java.util.Random;

/**
 * User: WHPM-1031
 * Date: 5/6/2014
 * Time: 2:12 AM
 */
public class Mutate implements Action {
    private double mutatePercentage;
    private Random generator;

    public Mutate(double percentage) {
        this.mutatePercentage = percentage;
        this.generator = new Random();
    }

    public static boolean Probability(double percentage) {
        Random generator = new Random();
        return generator.nextInt(100) < percentage * 100;
    }

    @Override
    public State act(State cur) {
        Queens queens = (Queens) cur;
        if (Probability(mutatePercentage)) {
            Queen q = queens.get(generator.nextInt(queens.size()));
            q.setCoord(new Coordinate(q.getCoord().getX(), generator.nextInt(queens.size())));
        }
        return queens;
    }
}
