package nl.tue.algorithm;

import nl.tue.algorithm.pathindex.IndexQueryEstimator;
import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.algorithm.pathindex.PathSummary;
import nl.tue.io.Parser;

import java.util.Collection;

/**
 * Implementation of the summary datastructure that uses a very simple join algorithm.
 * <p>
 * Created by Nathan on 5/27/2016.
 */
public class NaiveJoinAlgorithm extends Algorithm {

    protected IndexQueryEstimator indexQueryEstimator = null;

    @Override
    public void buildSummary(Parser p, int maximalPathLength, long budget) {
        indexQueryEstimator = new IndexQueryEstimator();
        indexQueryEstimator.buildSummary(p, maximalPathLength, budget);
    }

    @Override
    public int query(int[] query) {
        Collection<PathSummary> pathSummaries = this.indexQueryEstimator.retrieveAllExactEstimations();

        PathIndex reqIndex = new PathIndex(query);

        if (pathSummaries.stream().anyMatch(pathSummary -> pathSummary.getIndex().equals(reqIndex))) {
            return pathSummaries.stream().filter(pathSummary -> pathSummary.getIndex().
                    equals(reqIndex)).findFirst().get().getTuples();
        } else {
            return this.indexQueryEstimator.guesstimate(query);
        }
    }

    @Override
    public long getBytesUsed() {
        return 0;
    }

    @Override
    public String getOutputName() {
        return getClass().getSimpleName();
    }
}