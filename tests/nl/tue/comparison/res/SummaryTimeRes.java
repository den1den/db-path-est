package nl.tue.comparison.res;

import nl.tue.comparison.TestEnvironment;
import nl.tue.io.graph.AdjacencyList;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Nathan on 6/16/2016.
 */
public class SummaryTimeRes {

    @Test
    public void computeSummaryTimeRes() {
        File biblio = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());
        File music = new File(AdjacencyList.class.getClassLoader().getResource("generatedmusicdata.txt").getFile());
        File cineast = new File(AdjacencyList.class.getClassLoader().getResource("cineasts.txt").getFile());

        TestEnvironment biblioEnv = new TestEnvironment(new ArrayList<>(), biblio, "biblio", false);
        TestEnvironment musicEnv = new TestEnvironment(new ArrayList<>(), music, "music", false);
        TestEnvironment cineaastEnv = new TestEnvironment(new ArrayList<>(), cineast, "cineast", false);
    }
}
