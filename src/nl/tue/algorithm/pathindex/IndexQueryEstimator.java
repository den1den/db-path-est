package nl.tue.algorithm.pathindex;

import nl.tue.algorithm.Algorithm;
import nl.tue.io.Parser;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Computes a summary for the given graph, after the summary has been computed the summary is serialized to a byte
 * array which is then stored in main memory. The deep memory usage of this class (overhead of this class, overhead of
 * the byte array and items in the byte array) is the memory usage used for the summary.
 * <p>
 * When this summary is queried it deserializes the byte array to a map of paths and summaries, which is then queried to
 * return the path recorded for the given query.
 * <p>
 * Created by Nathan on 5/24/2016.
 */
public class IndexQueryEstimator implements Algorithm {

    /**
     * 2 Bytes for a path index, one byte for start, stop and end, plus bytes for the separation characters
     * Be aware that this is a rough underestimation, this is fixed when the actual summary is serialized into memory.
     */
    private static final double STORAGE_PER_PATH_ESTIMATE = 2 + 2 + 2 + 2;

    /**
     * Overhead of the array + this class.
     */
    private static final double OVERHEAD = (12 + 4) + 16;

    /**
     * Right now the optimized graph is stored as an array of chars, where each path index and summary is delimited by
     * a '#'. A path index and summary consist out of a index, which is just PathIndex.getIndex(). This is delimited by
     * a '-', then there are three integers (start, tuples, end) stored as their string representation where start and
     * tuples are delimited by '-' and end delimited by '#' as it is the last element of the index and summary
     *
     * Possible idea is storing path indexes as bytes instead of string representations of labels.
     *
     * Possible idea is storing an identifier for s, t and e which denotes whether a byte, short, int or long is used
     * to serialize the three numbers.
     */
    private byte[] optimizedGraph;

    @Override
    public void buildSummary(Parser p, int k, double b) {

        int maxNoOfPaths = (int) Math.floor((b - OVERHEAD) / STORAGE_PER_PATH_ESTIMATE);

        Queue<PathSummary> fullSummary = IndexQueryEstimatorFactory.construct(p, k, maxNoOfPaths);

        /**
         * Serialize the fuck out of this.
         */

        optimizedGraph = summaryToByteArray(fullSummary);
    }

    @Override
    public int query(List<Long> query) {
        Map<PathIndex, Summary> pathIndexMap = indexFromOptimizedArray(optimizedGraph);

        int[] path = new int[query.size()];

        for (int i = 0; i < query.size(); i++) {
            path[i] = query.get(i).intValue();
        }

        PathIndex requested = new PathIndex(path);

        if (pathIndexMap.containsKey(requested)) {
            return pathIndexMap.get(requested).getTuples();
        } else {
            return -1;
        }
    }

    @Override
    public long getBytesUsed() {
        return 0;
    }

    private static byte[] summaryToByteArray(Queue<PathSummary> summaries) {
        List<Byte> output = new ArrayList<>();

        for (PathSummary summary : summaries) {
            String summaryAsString = String.format("%s-%d-%d-%d#", summary.getIndex().getPath(),
                    summary.getSummary().getStart(), summary.getSummary().getTuples(), summary.getSummary().getEnd());


            for (byte b : summaryAsString.getBytes(StandardCharsets.US_ASCII)) {
                output.add(b);
            }
        }

        byte[] out = new byte[output.size() - 1];

        for (int i = 0; i < out.length; i++) {
            out[i] = output.get(i);
        }

        return out;
    }

    private static final Map<PathIndex, Summary> indexFromOptimizedArray(byte[] array) {
        Map<PathIndex, Summary> res = new HashMap<>();

        String arrayAsString = new String(array);

        for (String item : arrayAsString.split("#")) {
            String[] subItems = item.split("-");

            PathIndex index = new PathIndex(subItems[0]);

            Summary summary = new Summary(Integer.parseInt(subItems[1]), Integer.parseInt(subItems[2]),
                    Integer.parseInt(subItems[3]));

            res.put(index, summary);
        }

        return res;
    }

}
