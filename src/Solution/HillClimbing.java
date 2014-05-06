package Solution;

import action.Action;
import action.MoveOnCol;
import eval.ConflictQueens;
import eval.Heuristics;
import model.*;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 3:31 PM
 */
public class HillClimbing {
    private Node root;
    private Heuristics heuris;

    public HillClimbing(int size) {
        heuris = new ConflictQueens();
        root = new Plain(size, heuris);
    }

    private boolean ReachGoal(Node node) {
        return heuris.eval(node.getState()) == 0;
    }

    public Node Run() {
        Node cur = root;
        while (cur != null) {
            cur = Climb(cur);
            if (ReachGoal(cur)) {
                return cur;
            }
        }

        return null;
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

        if (newPlain.CostEval() < cur.CostEval()) {
            return newPlain;
        }

        return null;
    }
}
