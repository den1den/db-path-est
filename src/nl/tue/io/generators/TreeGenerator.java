package nl.tue.io.generators;

import nl.tue.io.TupleList;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Dennis on 10-6-2016.
 */
public class TreeGenerator {
    int treeHeads;
    int maxDepth;
    double avgBranching;
    double branchingStd;

    /**
     * labelChances[i] = change to get label i;
     */
    double[] labelChances;

    Random random = new Random(5798435793L);


    /**
     * @param treeHeads    number of trees
     * @param maxDepth     the target maxDepth
     * @param avgBranching avg branching factor
     * @param branchingStd deviation in branching factor
     * @param labelChances labelChances[i] = change to get label i, total = 1
     */
    public TreeGenerator(int treeHeads, int maxDepth, double avgBranching, double branchingStd, double[] labelChances) {
        this.treeHeads = treeHeads;
        this.maxDepth = maxDepth;
        this.avgBranching = avgBranching;
        this.branchingStd = branchingStd;
        this.labelChances = labelChances;
        assert Arrays.stream(labelChances).sum() == 1;
    }

    int NEXT_NODE = 0;

    private TupleList edges = new TupleList();

    public TupleList construct() {
        for (int _t = 0; _t < treeHeads; _t++) {
            int node = NEXT_NODE++;
            createChildren(node, 0);
        }
        return edges;
    }

    private void createChildren(int node, int depth) {
        if (depth >= this.maxDepth) {
            return;
        }
        double branchingFactor = Math.round((random.nextGaussian() + this.avgBranching) * this.branchingStd);
        if (branchingFactor <= 0) {
            return;
        }
        for (int child = 0; child < branchingFactor; child++) {
            int childNode = NEXT_NODE++;
            //Set a label fro this edge from parent to child
            int label = pickLabel();
            // Add this edge to the graph
            addEdge(node, label, childNode);
            createChildren(childNode, depth + 1);
        }
    }

    private void addEdge(int a, int label, int b) {
        edges.add(new int[]{a, label, b});
    }

    /**
     * @return 0 and labelChances.length - 1
     */
    public int pickLabel() {
        double r = random.nextDouble();
        int i;
        for (i = 0; i < labelChances.length; i++) {
            if (r < labelChances[i]) {
                return i;
            }
            r -= labelChances[i];
        }
        return labelChances.length - 1;
    }

    public int getNumberOfNodes() {
        return NEXT_NODE;
    }
}
