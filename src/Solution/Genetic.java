package solution;

import action.Action;
import action.CrossOver;
import action.DualAction;
import action.Mutate;
import eval.NonConflictedQueens;
import model.Node;
import model.Plain;
import model.State;

import java.util.*;
import java.util.stream.Collectors;

/**
 * User: WHPM-1031
 * Date: 5/6/2014
 * Time: 1:34 AM
 */
public class Genetic {
    private int populationSize = 1000;
    private final int maxNonImproved = 200;
    private final int maxCatastrophe = 50;
    private final double mutate_percentage = 0.2;
    private final double catastrophe_percentage = 0.3;
    private final double elite_percentage = 0.3;
    private int parentSize;

    private final List<Node> initGeneration;

    public Genetic(int size) {
        if (populationSize % 2 != 0) {
            populationSize++;
        }
        parentSize = populationSize;

        initGeneration = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            initGeneration.add(new Plain(size, new NonConflictedQueens()));
        }
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

    private List<Double> CalcProbability(List<Node> nodes) {
        List<Double> probability = new ArrayList<>(nodes.size());
        int sum = nodes.stream().mapToInt(Node::FitnessEval).sum();
        probability.addAll(nodes.stream().map(node -> ((double) node.FitnessEval()) / sum).collect(Collectors.toList()));
        return probability;
    }

    private List<Node> Next(List<Node> curGeneration) {
        List<Double> probability = CalcProbability(curGeneration);
        Queue<Node> parents = new PriorityQueue<>(parentSize);
        while (parents.size() < parentSize) {
//            Node parent = RoundRobinSelection(curGeneration, probability);
            Node parent = BinarySelection(curGeneration);
            if (parent != null) {
                parents.add(parent);
            }
        }

        Queue<Node> children = new PriorityQueue<>();
        int elite_size = (int) (elite_percentage * parents.size());
        List<Node> elite = new ArrayList<>(elite_size);
        while (children.size() < populationSize) {
            Node parentX = parents.poll();
            Node parentY = parents.poll();
            children.addAll(Mutate(ReProduce(parentX, parentY)));
            if (elite.size() < elite_size) {
                elite.add(parentX);
                elite.add(parentY);
            }
        }
        children.addAll(elite);


        List<Node> ret = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize && children.size() > 0; i++) {
            ret.add(children.poll());
        }

        return ret;
    }

    private List<Node> Catastrophe(List<Node> nodes, double factor) {
        int index = (int) (nodes.size() * factor);

        return nodes.subList(index, nodes.size());
    }

    public Node Run(){
        List<Node> curGeneration = initGeneration;
        int countUnimproved = 0;
        int count_catastrophe = 0;
        while (count_catastrophe < maxCatastrophe) {
            if (countUnimproved < maxNonImproved){
                Node bestLast = curGeneration.get(0);
                if (bestLast.ReachGoal()) {
                    return bestLast;
                }

                curGeneration = Next(curGeneration);
                Node bestCur = curGeneration.get(0);
                if (bestCur.compareTo(bestLast) < 0){
                    countUnimproved = 0;
                }
                else {
                    countUnimproved++;
                }
            }
            else {
                curGeneration = Catastrophe(curGeneration, catastrophe_percentage);
                countUnimproved = 0;
                count_catastrophe++;
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
        Action action = new Mutate(mutate_percentage);
        List<Node> ret = new ArrayList<>(children.size());
        ret.addAll(children.stream().map(node -> new Plain(action.act(node.getState()), node.getHeuristic())).collect(Collectors.toList()));
        return ret;
    }




}
