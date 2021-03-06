package nl.tue;

import nl.tue.algorithm.Algorithm;
import nl.tue.algorithm.Algorithm_Brute;
import nl.tue.io.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    // Subgraph approach:
    // paramater: C \in (0,1) percentage of memory used for subgraph only. 1-C is used for scalingfacotrs
    // 1. Devide complete graph into subset of nodes/edges.
    // 2. Store complete graph on these nodes/edges.
    // 2.1 Minimize graph -> packing
    // 3. Store for each subquery a factor = (Subset/Real) to scale it with -> used for combinations
    // On query:
    // 1 unpack the stored subgraph
    // 2 query the subgraph completely on full query
    // 3 split up the query in subqueries that are in memory
    // 4 getStored the scaling factor for each subquery
    // 5 Combine scaling factor from subquries to getStored a final scaling factor
    // 6 Multiple subquery result with scaling factor to getStored final result, different is not in memory

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        long maximalPathLength = Long.parseLong(args[1]);
        long budget = Long.parseLong(args[2]);

        Parser p = new Parser();
        p.parse(file);

        // Create OG
        Algorithm algorithm = new Algorithm_Brute();
        algorithm.buildSummary(p, (int) maximalPathLength, budget);

        // Write actual bytes used to System.out
        long bytes = algorithm.getBytesUsed();
        System.out.println(bytes);

        Scanner s = new Scanner(System.in);
        while (s.hasNextLine()) {
            String nextLine = s.nextLine();

            long result = algorithm.query(translateTextQueryToDomainQuery(nextLine, p));

            System.out.println(result);
        }
    }

    public static int[] translateTextQueryToDomainQuery(String query, Parser parser) {
        String[] parts = query.split(" ");
        int[] out = new int[parts.length / 2];

        for (int i = 0; i < parts.length; i += 2) {
            out[i/2] = parser.getEdgeMappings().get(parts[i] + parts[i+1]);
        }

        return out;
    }

    /**
     * Read and parse all files from some folder
     *
     * @param folder
     * @return
     */
    private static Iterable<Parser> readAll(String folder) {
        File baseFolder = new File(folder);
        if (!baseFolder.isDirectory()) {
            throw new RuntimeException("Expected first argument to be a directory, instead found: " + baseFolder.toString());
        }
        File[] files = baseFolder.listFiles((File dir, String name) -> name.endsWith(".txt"));
        Iterator<Parser> it = Arrays.stream(files).map(file -> {
            Parser p = new Parser();
            try {
                p.parse(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return p;
        }).iterator();
        return () -> it;
    }
}
