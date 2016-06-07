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
public class NaiveJoinAlgorithm extends Algorithm<IndexQueryEstimator> {

    @Override
    protected IndexQueryEstimator build(Parser p, int maximalPathLength, long budget) {
        IndexQueryEstimator estimator = new IndexQueryEstimator();
        estimator.buildSummary(p, maximalPathLength, budget);
        return estimator;
    }

    @Override
    public int query(int[] query) {
        Collection<PathSummary> pathSummaries = this.inMemory.retrieveAllExactEstimations();

        PathIndex reqIndex = new PathIndex(query);

        if (pathSummaries.stream().anyMatch(pathSummary -> pathSummary.getIndex().equals(reqIndex))) {
            return pathSummaries.stream().filter(pathSummary -> pathSummary.getIndex().
                    equals(reqIndex)).findFirst().get().getTuples();
        } else {
            return this.inMemory.guesstimate(query);
        }
    }

    @Override
    protected long bytesOverhead() {
        return 0;
    }
}