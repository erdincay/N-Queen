package solution;

import action.Action;
import action.MoveOnCol;
import eval.ConflictQueens;
import model.*;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 3:31 PM
 */
public class HillClimbing {
    private final Node root;
    private int sidewaysCount;
    private final int sidewaysThreshold = 15;

    public HillClimbing(int size) {
        sidewaysCount = 0;
        root = new Plain(size, new ConflictQueens());
    }

    private boolean ReachGoal(Node node) {
        return node.FitnessEval() == 0;
    }

    public Node Run() {
        Node cur = null;
        Node next = root;
        while (next != cur) {
            cur = next;
            next = Climb(cur);
            if (ReachGoal(next)) {
                return next;
            }
        }

        return next;
    }

    private Node Climb(Node cur) {
        Queens queens = (Queens) cur.getState();
        Queue<Node> queensList = new PriorityQueue<Node>();

        for (int i = 0; i < queens.size(); i++) {
            Coordinate coord = queens.get(i).getCoord();
            for (int j = 0; j < queens.size(); j++) {
                int step = j - coord.getY();
                if (step != 0){
                    Action movement = new MoveOnCol(i,step);
                    State newQueens = movement.act(queens);
                    queensList.add(new Plain(newQueens, cur.getHeuristic()));
                }
            }
        }

        Node newPlain = queensList.poll();

        int newFit = newPlain.FitnessEval();
        int curFit = cur.FitnessEval();
        if (newFit < curFit) {
            sidewaysCount = 0;
            return newPlain;
        }
        else if (newFit == curFit && sidewaysCount < sidewaysThreshold) {
            sidewaysCount++;
            return newPlain;
        }

        sidewaysCount = 0;
        return cur;
    }
}
