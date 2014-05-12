package solution;

import model.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * User: Ding
 * Date: 4/25/2014
 * Time: 10:50 AM
 */
public class Report {
    private class ReportData {
        public final long run_time;
        public final int run_cost;
        public final boolean bPerfect;

        public ReportData(long run_time, int run_cost, boolean bPerfect) {
            this.run_time = run_time;
            this.run_cost = run_cost;
            this.bPerfect = bPerfect;
        }
    }

    private class ReportStatistic {
        public final long avg_time;
        public final int avg_cost;
        public final double correct_accuracy;

        private ReportStatistic(long avg_time, int avg_cost, double correct_accuracy) {
            this.avg_time = avg_time;
            this.avg_cost = avg_cost;
            this.correct_accuracy = correct_accuracy;
        }
    }

    private long start_time;
    private final Map<String, List<ReportData>> datas;

    public Report()
    {
        datas = new HashMap<>();
    }

    public void StartRecord() {
        start_time = System.currentTimeMillis();

    }

    public void EndRecord(Solution sln, Node ret) {
        RecordReport(System.currentTimeMillis() - start_time, sln, ret);
    }

    private void AddData(final String tag, final long time, final int cost, final boolean bPerfect) {
        if (datas.containsKey(tag)) {
            datas.get(tag).add(new ReportData(time, cost, bPerfect));
        }
        else {
            datas.put(tag, new ArrayList<ReportData>(){{add(new ReportData(time, cost, bPerfect));}});
        }
    }

    private void RecordReport(final long time, Solution sln, Node ret) {
        if (ret != null) {
            AddData(sln.toString(), time, sln.getTrails(), ret.ReachGoal());
        }
    }

    private ReportStatistic CalcAverage(List<ReportData> records) {
        if (records.size() <= 0) {
            return null;
        }

        long sum_time = 0;
        int sum_cost = 0;
        int sum_perfect = 0;

        for (ReportData rd : records) {
            sum_cost += rd.run_cost;
            sum_time += rd.run_time;
            if (rd.bPerfect) {
                sum_perfect++;
            }
        }

        return new ReportStatistic(sum_time/records.size(), sum_cost / records.size(), ((double)sum_perfect) / records.size());
    }

    public void RunReport(final String filename) {
        File file = new File(filename);
        try {
            FileWriter writer = new FileWriter(file,true);

            for (String tag : datas.keySet()) {
                writer.write(tag + " ");
            }
            writer.write(System.lineSeparator());

            for (List<ReportData> cases : datas.values()) {
                ReportStatistic average = CalcAverage(cases);
                writer.write(average.avg_cost + " " + average.avg_time + " " + new DecimalFormat("#00.00").format(average.correct_accuracy * 100) + "% " + cases.size() + " ");
            }
            writer.write(System.lineSeparator());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reportNode(String filename, Node end) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(end.toString());
            writer.write(System.lineSeparator());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
