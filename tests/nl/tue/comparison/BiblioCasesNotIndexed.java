package nl.tue.comparison;

import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.io.Parser;
import nl.tue.io.graph.AdjacencyList;
import org.junit.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nathan on 5/25/2016.
 */
public class BiblioCasesNotIndexed {

    private static File biblioFile = new File(AdjacencyList.class.getClassLoader().getResource("biblio.txt").getFile());

    private static Parser parser;

    /**
     * These paths will not be indexed if byte allocation is set to nodes * 2.
     */
    private static List<PathIndex> paths = Arrays.asList(new PathIndex[] {
            new PathIndex("5/0/4/1/2"), new PathIndex("5/0/4/3"), new PathIndex("5/0/1/2"), new PathIndex("0/4/1/2")
    });

    @BeforeClass
    public static void beforeClass() throws IOException {
        parser = new Parser();

        parser.parse(biblioFile);

        /**
         * Figure out how to instantiate the algorithm class. TODO
         */
    }
}
