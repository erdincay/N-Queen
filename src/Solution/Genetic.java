package Solution;

import action.Action;
import action.CrossOver;
import action.DualAction;
import action.Mutate;
import eval.ConflictQueens;
import model.*;

import java.util.*;

/**
 * Created by WHPM-1031 on 5/6/2014.
 */
public class Genetic {
    private final int populationSize = 4;
    private final int maxGeneration = 100;
    private int parentSize;

    private final Queue<Node> initGeneration;

    public Genetic(int size) {
        parentSize = calcCombinationSize();
        initGeneration = new PriorityQueue<Node>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            initGeneration.add(new Plain(size, new ConflictQueens()));
        }
    }

    private boolean ReachGoal(Node node) {
        return node.FitnessEval() == 0;
    }

    public static double choose(int x, int y) {
        if (y < 0 || y > x) return 0;
        if (y > x/2) {
            y = x - y;
        }

        double answer = 1.0;
        for (int i = 1; i <= y; i++) {
            answer *= (x + 1 - i);
            answer /= i;
        }
        return answer;
    }

    public int calcCombinationSize(){
        for (int i = 2; i <= populationSize; i++) {
            if (choose(i,2) * 2 >= populationSize) {
                return i;
            }
        }

        return populationSize;
    }

    private Queue<Node> Next(Queue<Node> curGeneration) {
        List<Node> parents = new ArrayList<Node>(parentSize);
        for (int i = 0; i < parentSize; i++) {
            parents.add(curGeneration.poll());
        }

        List<Node> children = new ArrayList<Node>();
        for (int i = 0; i < parents.size(); i++) {
            for (int j = i+1; j < parents.size(); j++) {
                children.addAll(ReProduce(parents.get(i), parents.get(j)));
            }
        }

        Queue<Node> temp = new PriorityQueue<Node>();
        temp.addAll(Mutate(children));
        temp.addAll(parents);

        Queue<Node> ret = new PriorityQueue<Node>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            ret.add(temp.poll());
        }

        return ret;
    }

    public Node Run(){
        Queue<Node> curGeneration = initGeneration;
        for (int i = 0; i < maxGeneration; i++) {
            if (ReachGoal(curGeneration.peek())) {
                return curGeneration.poll();
            }
            curGeneration = Next(curGeneration);
        }

        return curGeneration.poll();
    }

    private List<Node> ReProduce(Node x, Node y) {
        DualAction dction = new CrossOver();
        List<State> states = dction.act(x.getState(), y.getState());
        List<Node> ret = new ArrayList<Node>(2);
        for (State s : states){
            ret.add(new Plain(s, x.getHeuristic()));
        }

        return ret;
    }

    private List<Node> Mutate(List<Node> children) {
        Action action = new Mutate();
        List<Node> ret = new ArrayList<Node>(children.size());
        for (Node node : children) {
            ret.add(new Plain(action.act(node.getState()),node.getHeuristic()));
        }
        return ret;
    }




}
