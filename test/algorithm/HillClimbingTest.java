package algorithm;

import model.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Solution.HillClimbing;

/**
 * User: Ding
 * Date: 5/6/2014
 * Time: 5:01 PM
 */
public class HillClimbingTest {
    private final int size = 15;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        HillClimbing hc = new HillClimbing(size);
        Node sln = hc.Run();

        System.out.println(sln);
    }

    @Test
    public void testStatistic() {
        int count = 0;
        int trails = 1000;
        for (int i = 0; i < trails; i++) {
            HillClimbing hc = new HillClimbing(size);
            Node sln = hc.Run();
            if (sln.ReachGoal()) {
                count++;
            }
        }

        System.out.print((double) count / trails);
    }
}
