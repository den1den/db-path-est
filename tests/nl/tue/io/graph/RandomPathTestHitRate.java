package nl.tue.io.graph;

import nl.tue.io.Parser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Nathan on 6/7/2016.
 */
public class RandomPathTestHitRate {
    private static File file = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());

    private static AdjacencyList list;

    private static Parser parser;

    @BeforeClass
    public static void loadFile() throws IOException {
        parser = new Parser();

        parser.parse(file);

        list = new AdjacencyList(parser);

    }


    @Test
    public void reportRandomPathHitRate() {
        Random random = new Random();

        int tries = 0;
        int nonZero = 0;

        do {

            int[] path = new int[5];

            for (int i = 0; i < path.length; i++) {
                path[i] = random.nextInt(parser.getNLabels());
            }

            tries++;
            if(list.solvePathQuery(path).size() > 0) {
                nonZero++;
            }
        } while(nonZero < 10);

        System.out.println(String.format("Random hitrate is %f", (double)nonZero / (double)tries));

    }

    @Test
    public void reportEdgeBasedPathHitRate() {
        Random random = new Random();

        int tries = 0;
        int nonZero = 0;

        List<Double> buckets = new ArrayList<>();

        int edgeCount = list.totalEdges();

        for(int i = 0; i < parser.getNLabels(); i++) {
            double fraction = (double)list.getOutgoingIndex().get(i).size() / edgeCount;

            if(i == 0) {
                buckets.add(fraction);
            } else {

                buckets.add(fraction + buckets.get(i - 1));
            }
        }

        do {

            int[] path = new int[5];

            for (int i = 0; i < path.length; i++) {
                double randVal = random.nextDouble();

                int label = 0;

                for(int j = 0; j < parser.getNLabels(); j++) {
                    if(buckets.get(j) < randVal) {
                        label = j;
                    } else {
                        label++;
                        break;
                    }
                }

                path[i] = label;
            }

            tries++;
            if(list.solvePathQuery(path).size() > 0) {
                nonZero++;
            }
        } while(nonZero < 10);

        System.out.println(String.format("Edge based hitrate is %f", (double)nonZero / (double)tries));

    }

    @Test
    public void reportEdgeBasedWithReversalPathHitRate() {
        Random random = new Random();

        int tries = 0;
        int nonZero = 0;

        List<Double> buckets = new ArrayList<>();

        int edgeCount = list.totalEdges();

        for(int i = 0; i < parser.getNLabels(); i++) {
            double fraction = (double)list.getOutgoingIndex().get(i).size() / edgeCount;

            if(i == 0) {
                buckets.add(fraction);
            } else {

                buckets.add(fraction + buckets.get(i - 1));
            }
        }

        do {

            int[] path = new int[5];

            for (int i = 0; i < path.length; i++) {
                double randVal = random.nextDouble();

                int label = 0;

                for(int j = 0; j < parser.getNLabels(); j++) {
                    if(buckets.get(j) < randVal) {
                        label = j;
                    } else {
                        label++;
                        break;
                    }
                }

                path[i] = label;



                if(i != path.length - 1) {
                    randVal = random.nextDouble();

                    i++;

                    if(randVal < .33) {
                        if(label < parser.getNLabels()/2) {
                            path[i] = label + parser.getNLabels()/2;
                        } else {
                            path[i] = label - parser.getNLabels()/2;
                        }

                    }
                }
            }

            tries++;
            if(list.solvePathQuery(path).size() > 0) {
                nonZero++;
            }
        } while(nonZero < 10);

        System.out.println(String.format("Edge based with reversal hitrate is %f", (double)nonZero / (double)tries));

    }
}
