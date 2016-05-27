package nl.tue.algorithm;

import nl.tue.algorithm.pathindex.IndexQueryEstimator;
import nl.tue.algorithm.pathindex.PathIndex;
import nl.tue.algorithm.pathindex.PathSummary;

import java.util.Collection;

/**
 * Implementation of the summary datastructure that uses a very simple join algorithm.
 *
 * Created by Nathan on 5/27/2016.
 */
public class NaiveJoinAlgorithm extends Algorithm<PathSummary, IndexQueryEstimator> {

    public NaiveJoinAlgorithm(IndexQueryEstimator estimator) {
        super(estimator);
    }

    @Override
    protected int executeQuery(int[] query) {
        Collection<PathSummary> pathSummaries = this.inMemoryEstimator.retrieveAllExactEstimations();

        PathIndex reqIndex = new PathIndex(query);

        if(pathSummaries.stream().anyMatch(pathSummary -> pathSummary.getIndex().equals(reqIndex))) {
            return pathSummaries.stream().filter(pathSummary -> pathSummary.getIndex().
                    equals(reqIndex)).findFirst().get().getTuples();
        } else {
            return this.inMemoryEstimator.guesstimate(query);
        }
    }
}
