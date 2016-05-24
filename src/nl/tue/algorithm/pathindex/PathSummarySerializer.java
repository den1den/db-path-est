package nl.tue.algorithm.pathindex;

import java.nio.charset.StandardCharsets;

/**
 * Created by Nathan on 5/24/2016.
 */
public class PathSummarySerializer {

    public static byte[] serialize(PathSummary summary) {
        String summaryAsString = String.format("%s-%d-%d-%d#", summary.getIndex().getPath(),
                summary.getSummary().getStart(), summary.getSummary().getTuples(), summary.getSummary().getEnd());


        return summaryAsString.getBytes(StandardCharsets.US_ASCII);
    }

}
