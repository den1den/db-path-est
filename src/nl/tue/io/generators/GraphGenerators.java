package nl.tue.io.generators;

import nl.tue.io.RawTuples;

import java.util.Random;

/**
 * Created by Dennis on 10-6-2016.
 */
public class GraphGenerators {
    /**
     * Adrian
     * @param r
     * @param maxLabels
     * @param nrOfNodes
     * @param addMoreRandom
     * @return
     */
    public static RawTuples GenerateGraph(Random r, int maxLabels, int nrOfNodes, boolean addMoreRandom) {
        RawTuples tuples = new RawTuples();

        for (int i = 0; i < nrOfNodes; i++) {
            int src = i + 1;
            int label = r.nextInt(maxLabels) + 1;
            int dst = r.nextInt(nrOfNodes) + 1;
            tuples.add(src, label, dst);
        }
        if (addMoreRandom) {
            for (int i = 0; i < nrOfNodes; i++) {
                int src = r.nextInt(nrOfNodes) + 1;
                int label = r.nextInt(maxLabels) + 1;
                int dst = r.nextInt(nrOfNodes) + 1;
                tuples.add(src, label, dst);
            }
        }
        return tuples;
    }
}
