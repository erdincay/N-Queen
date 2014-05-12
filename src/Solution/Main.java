package solution;

import model.Node;

public class Main {

    private static final String paraAlgorithm = "alg";
    private static final String strGenetic = "genetic";
    private static final String paraSize = "size";
    private static final String paraOutput = "output";

    public static void main(String[] args) {
        int size = 15;
        String strAlg = "";
        String strOutput = "";
        for (String arg : args) {
            String[] para = arg.split("=");
            if (para.length == 2) {
                if(para[0].toLowerCase().contains(paraAlgorithm)) {
                    strAlg = para[1];
                }
                else if (para[0].toLowerCase().contains(paraOutput)) {
                    strOutput = para[1];
                }
                else if (para[0].toLowerCase().contains(paraSize)) {
                    size = Integer.parseInt(para[1]);
                }
            }
        }

        Solution sln;
        if (strAlg.contains(strGenetic)) {
            sln = new Genetic(size);
        }
        else {
            sln = new HillClimbing(size);
        }

        Node ret = sln.Run();
        if (sln == null) {
            System.out.println(size + " Queens failed!");
        } else {
            System.out.println("Puzzle success on: " + System.lineSeparator() + ret.toString());

            if (!strOutput.isEmpty()) {
                Report.reportNode(strOutput, ret);
            }
        }
    }
}
