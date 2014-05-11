package genetic;

import model.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import solution.Genetic;

public class GeneticTest {
    private final int size = 15;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        Genetic gc = new Genetic(size);
        Node sln = gc.Run();

        System.out.println(sln);
    }

    @Test
    public void testStatistic() {
        int count = 0;
        int trails = 0;
        for (int i = 0; i < trails; i++) {
            Genetic gc = new Genetic(size);
            Node sln = gc.Run();
            if (sln.ReachGoal()) {
                count++;
            }
        }

        System.out.print((double) count / trails);
    }
}