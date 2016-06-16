package nl.tue.comparison.res;

import nl.tue.algorithm.subgraph.SubgraphHighKFactorAlgorithm;
import nl.tue.comparison.TestEnvironment;
import nl.tue.io.graph.AdjacencyList;
import org.junit.Test;
import org.omg.CORBA.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathan on 6/16/2016.
 */
public class SummaryTimeRes {

    @Test
    public void computeSummaryTimeRes() throws IOException {
        File biblio = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());
        File music = new File(AdjacencyList.class.getClassLoader().getResource("generatedmusicdata.txt").getFile());
        File cineast = new File(AdjacencyList.class.getClassLoader().getResource("cineasts.txt").getFile());

        File output = new File("summary_time.csv");

        output.delete();

        output.createNewFile();

        PrintWriter writer = new PrintWriter(output);

        writer.println(String.format("edges,sTime,dataset"));

        for(int j = 0; j < 12; j++) {

            List<TestEnvironment> envs = new ArrayList<>();

            int parts = 8;

            for (int i = 1; i <= parts; i++) {
                envs.add(new TestEnvironment(new ArrayList<String>(), biblio, "biblio", false, parts, i));
                envs.add(new TestEnvironment(new ArrayList<String>(), music, "music", false, parts, i));
                envs.add(new TestEnvironment(new ArrayList<String>(), cineast, "cineast", false, parts, i));
            }

            for (TestEnvironment env : envs) {
                SubgraphHighKFactorAlgorithm algo = new SubgraphHighKFactorAlgorithm();

                env.execute(algo);

                String o = String.format("%d,%d,%s", env.getEdges(), env.getSummaryTime(), env.getName());
                writer.println(o);
                System.out.println(o);
            }
            writer.flush();
        }
        writer.close();
    }
}
