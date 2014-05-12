package report;

import model.Node;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import solution.Genetic;
import solution.HillClimbing;
import solution.Report;

import static org.junit.Assert.assertNotNull;

/**
 * User: Ding
 * Date: 4/25/2014
 * Time: 11:28 AM
 */
public class ReportTest {
    private int size;
    private int trails;


    private static final Report report = new Report();
    @Before
    public void setUp() throws Exception {
        size = 15;
        trails = 5000;
    }

    @Test
    public void testHillClimbing() throws Exception {
        for (int i = 0; i < trails; i++) {
            HillClimbing hc = new HillClimbing(size);

            report.StartRecord();
            Node ret = hc.Run();
            report.EndRecord(hc,ret);

            assertNotNull(ret);
        }
    }

    @Test
    public void testGenetic() throws Exception {
        for (int i = 0; i < trails; i++) {
            Genetic gc = new Genetic(size);

            report.StartRecord();
            Node ret = gc.Run();
            report.EndRecord(gc, ret);

            assertNotNull(ret);
        }
    }

    @AfterClass
    public static void OneTimeTeardown() {
        report.RunReport("log2.txt");
    }
}
