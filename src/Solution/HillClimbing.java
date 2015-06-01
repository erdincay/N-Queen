package Solution;

import action.Action;
import action.MoveOnCol;
import eval.ConflictedQueens;
import model.*;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * User: Ding
 * Date: 5/5/2014
 * Time: 3:31 PM
 */
public class HillClimbing implements Solution {
    private final Node root;
    private int sidewaysCount;
    private final int sidewaysThreshold = 10;
    private int count_climbing = 0;

    @Override
    public int getTrials() {
        return count_climbing;
    }

    public HillClimbing(int size) {
        sidewaysCount = 0;
        root = new Plain(size, new ConflictedQueens());
    }

    @Override
    public Node Run() {
        Node cur = null;
        Node next = root;
        while (next != cur) {
            cur = next;
            next = Climb(cur);
            if (next.ReachGoal()) {
                return next;
            }
        }

        return next;
    }

    private Node Climb(Node cur) {
        Queens queens = (Queens) cur.getState();
        Queue<Node> queensList = new PriorityQueue<>();

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

        int compareRet = newPlain.compareTo(cur);
        if (compareRet < 0) {
            sidewaysCount = 0;
            count_climbing++;
            return newPlain;
        }
        else if (compareRet == 0 && sidewaysCount < sidewaysThreshold) {
            sidewaysCount++;
            count_climbing++;
            return newPlain;
        }

        sidewaysCount = 0;
        count_climbing++;
        return cur;
    }

    @Override
    public String toString() {
        return HillClimbing.class.getName();
    }
}
