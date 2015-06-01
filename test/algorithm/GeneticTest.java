package algorithm;

import model.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Solution.Genetic;

import java.text.DecimalFormat;

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
        Genetic gc = new Genetic(size, size);
        Node sln = gc.Run();

        System.out.println(sln);
    }

    @Test
    public void testStatistic() {
        int count = 0;
        int trails = 1000;
        for (int i = 0; i < trails; i++) {
            Genetic gc = new Genetic(size, size);
            Node sln = gc.Run();
            if (sln.ReachGoal()) {
                count++;
            }
        }

        System.out.println(new DecimalFormat("#0.0000").format((double) count / (double)trails));
    }
}