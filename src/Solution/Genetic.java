package solution;

import action.Action;
import action.CrossOver;
import action.DualAction;
import action.Mutate;
import eval.NonConflictedQueens;
import model.Node;
import model.Plain;
import model.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * User: WHPM-1031
 * Date: 5/6/2014
 * Time: 1:34 AM
 */
public class Genetic implements Solution {
    private static final Logger LOG = Logger.getLogger(Genetic.class.getName());

    private int populationSize;
    private final int maxNonImproved = 500;
    private final int maxCatastrophe = 50;
    private int count_unimproved = 0;
    private int count_catastrophe = 0;
    private int count_generation = 0;

    private final List<Node> initGeneration;

    @Override
    public int getTrails() {
        return count_generation;
    }

    public double getMutate_percentage() {
        double base = 0.1;
        return base + ((double) count_unimproved / (maxNonImproved * 2)) + ((double) count_unimproved / (maxNonImproved * 2)) * ((double) count_catastrophe / (maxCatastrophe));
    }

    public double getCatastrophe_percentage() {
        double base = 0.5;
        return ((double) count_catastrophe / maxCatastrophe) * base;
    }

    public double getElite_percentage() {
        return 0.1;
    }

    public Genetic(int size) {
        populationSize = size;
        if (populationSize % 2 != 0) {
            populationSize++;
        }

        initGeneration = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            initGeneration.add(new Plain(size, new NonConflictedQueens()));
        }
        Collections.sort(initGeneration);
    }

    public static double choose(int x, int y) {
        if (y < 0 || y > x) return 0;
        if (y > x / 2) {
            y = x - y;
        }

        double answer = 1.0;
        for (int i = 1; i <= y; i++) {
            answer *= (x + 1 - i);
            answer /= i;
        }
        return answer;
    }

    public int calcCombinationSize(int size) {
        for (int i = 2; i <= size; i++) {
            if (choose(i, 2) * 2 >= size) {
                return i;
            }
        }

        return size;
    }

    private Node RoundRobinSelection(List<Node> nodes, List<Double> probability) {
        if (nodes.size() != probability.size()) {
            return null;
        }

        Random generator = new Random();
        double round = generator.nextDouble();

        double accumulate = 0.0;
        for (int i = 0; i < probability.size(); i++) {
            accumulate += probability.get(i);
            if (accumulate >= round) {
                return nodes.get(i);
            }
        }
        return null;
    }

    private Node BinarySelection(List<Node> nodes) {
        Random generator = new Random();
        int index1 = generator.nextInt(nodes.size());
        int index2 = generator.nextInt(nodes.size());
        Node node1 = nodes.get(index1);
        Node node2 = nodes.get(index2);

        return ((node1.compareTo(node2) < 0) ? node1 : node2);
    }

    protected Node DoubleBinarySelection(List<Node> nodes){
        Node node1 = BinarySelection(nodes);
        Node node2 = BinarySelection(nodes);

        return ((node1.compareTo(node2) < 0) ? node1 : node2);
    }

    private List<Double> CalcProbability(List<Node> nodes) {
        List<Double> probability = new ArrayList<>(nodes.size());
        int sum = nodes.stream().mapToInt(Node::FitnessEval).sum();
        probability.addAll(nodes.stream().map(node -> ((double) node.FitnessEval()) / sum).collect(Collectors.toList()));
        return probability;
    }

    private List<Node> Next(List<Node> curGeneration) {
        List<Node> children = new ArrayList<>(populationSize);
        while (children.size() < populationSize) {
            Node parentX = BinarySelection(curGeneration);
            Node parentY = BinarySelection(curGeneration);
            children.addAll(Mutate(ReProduce(parentX, parentY)));
        }
        children.addAll(curGeneration.subList(0, (int) (getElite_percentage() * curGeneration.size())));
        Collections.sort(children);

        count_generation++;
        return children.subList(0, populationSize);
    }

    private List<Node> Catastrophe(List<Node> nodes, double factor) {
        int index = (int) (nodes.size() * factor);

        return nodes.subList(index, nodes.size());
    }

    @Override
    public Node Run() {
        List<Node> curGeneration = initGeneration;
        while (count_catastrophe < maxCatastrophe) {
            if (count_unimproved < maxNonImproved) {
                Node bestLast = curGeneration.get(0);
                if (bestLast.ReachGoal()) {
                    return bestLast;
                }

                curGeneration = Next(curGeneration);

                if (curGeneration.size() != populationSize) {
                    LOG.log(Level.WARNING, "Population Size is " + curGeneration.size());
                }
                Node bestCur = curGeneration.get(0);
                if (bestCur.compareTo(bestLast) < 0) {
                    count_unimproved = 0;
                } else {
                    count_unimproved++;
                }
            } else {
                curGeneration = Catastrophe(curGeneration, getCatastrophe_percentage());
                count_unimproved = 0;
                count_catastrophe++;
//                LOG.log(Level.SEVERE, "Catastrophe count = " + count_catastrophe);
            }
        }

        return curGeneration.get(0);
    }

    private List<Node> ReProduce(Node x, Node y) {
        DualAction dction = new CrossOver();
        List<State> states = dction.act(x.getState(), y.getState());
        List<Node> ret = new ArrayList<>(2);
        ret.addAll(states.stream().map(s -> new Plain(s, x.getHeuristic())).collect(Collectors.toList()));

        return ret;
    }

    private List<Node> Mutate(List<Node> children) {
        Action action = new Mutate(getMutate_percentage());
        List<Node> ret = new ArrayList<>(children.size());
        ret.addAll(children.stream().map(node -> new Plain(action.act(node.getState()), node.getHeuristic())).collect(Collectors.toList()));
        return ret;
    }

    @Override
    public String toString() {
        return Genetic.class.getName();
    }
}
