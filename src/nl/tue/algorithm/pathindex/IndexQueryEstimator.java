package nl.tue.algorithm.pathindex;

import nl.tue.algorithm.Estimator;
import nl.tue.io.Parser;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Computes a summary for the given graph, after the summary has been computed the summary is serialized to a byte
 * array which is then stored in main memory. The deep memory usage of this class (overhead of this class, overhead of
 * the byte array and items in the byte array) is the memory usage used for the summary.
 * <p>
 * When this summary is queried it deserializes the byte array to a map of paths and summaries, which is then queried to
 * return the path recorded for the given getEstimation.
 * <p>
 * Created by Nathan on 5/24/2016.
 */
public class IndexQueryEstimator implements Estimator<PathSummary> {

    /**
     * If set to true debug information is printed by this class.
     */
    private static final boolean DEBUG = true;

    /**
     * 2 Bytes for a path index, one byte for start, stop and end, plus bytes for the separation characters
     * Be aware that this is a rough underestimation, this is fixed when the actual summary is serialized into memory.
     */
    private static final double STORAGE_PER_PATH_ESTIMATE = 2 + 2 + 2 + 2;

    /**
     * Overhead of the array + this class and there reference to the array.
     */
    private static final double OVERHEAD = (12 + 4) + (16 + 4);

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

        int maxNoOfPaths = (int) Math.floor((b - OVERHEAD) / STORAGE_PER_PATH_ESTIMATE) * 2;

        Queue<PathSummary> fullSummary = IndexQueryEstimatorFactory.construct(p, k, maxNoOfPaths);

        /**
         * Serialize the fuck out of this.
         */

        optimizedGraph = summaryToByteArray(fullSummary, (int) (b - OVERHEAD));
    }

    public int getEstimation(List<Long> query) {
        Map<PathIndex, Summary> pathIndexMap = indexFromOptimizedArray(optimizedGraph);

        int[] path = queryToIntArray(query);

        PathIndex requested = new PathIndex(path);

        if (pathIndexMap.containsKey(requested)) {
            return pathIndexMap.get(requested).getTuples();
        } else {
            return guesstimate(path, pathIndexMap);
        }
    }

    @Override
    public PathSummary combineEstimations(PathSummary left, PathSummary right) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Collection<PathSummary> retrieveAllExactEstimations() {
        throw new UnsupportedOperationException("TODO");
    }

    private static int guesstimate(int[] path, Map<PathIndex, Summary> pathIndexMap) {

        if(DEBUG) {
            System.out.println(String.format("Attempting to guess the result of %s", new PathIndex(path).getPath()));
        }

        for(PathIndex index : pathIndexMap.keySet()) {
            if(pathIndexMap.get(index).getTuples() == 0) {
                if(Collections.indexOfSubList(Arrays.asList(path), Arrays.asList(index.getPathAsIntArray())) != -1) {
                    return 0;
                }
            }
        }

        LinkedBlockingDeque<Summary> items = new LinkedBlockingDeque<>();

        int[] remainingPath = path;

        do {
            int index = remainingPath.length;

            do {
                int[] subPath = Arrays.copyOf(remainingPath, index);

                if(pathIndexMap.containsKey(new PathIndex(subPath))) {
                    items.add(pathIndexMap.get(new PathIndex(subPath)));

                    if(DEBUG) {
                        System.out.println(String.format("Identified %s as subpath", new PathIndex(subPath).getPath()));
                    }

                    break;

                } else {
                    index--;
                }
            } while(index > 0);

            remainingPath = Arrays.copyOfRange(remainingPath, index, remainingPath.length);
        } while(remainingPath.length > 0);

        do {
            Summary left = items.poll();
            Summary right = items.poll();


            int newTuples = (int)Math.min(((double)left.getTuples())*((double)right.getTuples()/(double)right.getStart()),
                    ((double)right.getTuples())*((double)left.getTuples()/(double)left.getEnd()));

            int newStart = left.getEnd();
            int newEnd = right.getStart();



            items.addFirst(new Summary(newStart, newTuples, newEnd));
        } while(items.size() > 1);

        return items.peek().getTuples();
    }

    @Override
    public long getBytesUsed() {
        return 0;
    }

    private static int[] queryToIntArray(List<Long> query) {
        int[] path = new int[query.size()];

        for (int i = 0; i < query.size(); i++) {
            path[i] = query.get(i).intValue();
        }

        return path;
    }

    private static byte[] summaryToByteArray(Queue<PathSummary> summaries) {
        return summaryToByteArray(summaries, -1);
    }

    /**

     * @param summaries
     * @param byteLimit Byte limit of the output array. Use -1 for infinite.
     * @return
     */
    private static byte[] summaryToByteArray(Queue<PathSummary> summaries, int byteLimit) {
        List<Byte> output = new ArrayList<>();

        int totalBytes = 0;
        int indexed = 0;

        for(PathSummary summary : summaries) {
            byte[] serialized = PathSummarySerializer.serialize(summary);

            if(byteLimit >= 0 && output.size() + serialized.length > byteLimit + 1 /* Last character is removed */) {
                break;
            }

            totalBytes += summary.getIndex().getPath().length() + 3*4;
            indexed++;

            for(byte b : serialized) {
                output.add(b);
            }
        }

        byte[] out = new byte[output.size() - 1];

        for (int i = 0; i < out.length; i++) {
            out[i] = output.get(i);
        }

        if(DEBUG) {
            System.out.println(String.format("Compressed %s bytes from %s paths into %s bytes which is less than the allotted " +
                    "amount of %s",
                    totalBytes, indexed, out.length, byteLimit));
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
