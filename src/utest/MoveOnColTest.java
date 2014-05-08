package utest;

import action.Action;
import action.MoveOnCol;
import model.Coordinate;
import model.Queens;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MoveOnColTest {
    private int size = 15;
    private Queens queens;
    private static FileWriter wt;
    static {
        try {
            wt = new FileWriter("MoveOnColTestLog.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeMsg(String msg) throws IOException {
        wt.write(msg);
        wt.write(System.lineSeparator());
    }

    private void showMsg(String msg) {
        System.out.println(msg);
    }

    @Before
    public void setUp() throws Exception {
        queens = new Queens(size);
//        for (int i = 0; i < size; i++) {
//            queens.add(new Queen(new Coordinate(i,i)));
//        }

        //writeMsg(queens.toString());
        //showMsg(queens.toString());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAct() throws Exception {
        List<List<Queens>> queensMatrix = new ArrayList<List<Queens>>(queens.size());
        for (int i = 0; i < queens.size(); i++) {
            Coordinate coord = queens.get(i).getCoord();
            List<Queens> queensList = new ArrayList<Queens>(queens.size());
            for (int j = 0; j < queens.size(); j++) {
                int step = j - coord.getY();
                Action movement = new MoveOnCol(i, step);
                Queens newQueens = (Queens) movement.act(queens);

//                showMsg(queens.toString());
//                showMsg(newQueens.toString());

                queensList.add(newQueens);
            }
            queensMatrix.add(queensList);
        }

        assertEquals(queens.size(), queensMatrix.size());
        for (int i = 0; i < queensMatrix.size(); i++) {
            List<Queens> queensList = queensMatrix.get(i);
            assertEquals(queens.size(), queensList.size());

            for (int j = 0; j < queensList.size(); j++) {
                Queens newqueens = queensList.get(j);
                assertEquals(queens.size(), newqueens.size());

                for (int k = 0; k < queens.size(); k++) {
                    Coordinate testCoord = queens.get(k).getCoord();
                    Coordinate newCoord = newqueens.get(k).getCoord();
                    if (k == i) {
                        assertEquals(testCoord.getX(), newCoord.getX());
                        assertEquals(j, newCoord.getY());
                    }
                    else {
                        assertEquals(testCoord, newCoord);
                    }
                }
            }
        }
    }
}