package nl.tue.algorithm;

import junit.framework.TestCase;
import nl.tue.algorithm.pathindex.IndexQueryEstimator;
import org.junit.Test;

/**
 * Created by dennis on 25-5-16.
 */
public class Algorithm_1Test extends TestCase {

    Algorithm_1 algorithm;

    @Override
    public void setUp() throws Exception {
        IndexQueryEstimator estimator = new IndexQueryEstimator();
        algorithm = new Algorithm_1<>(estimator);
    }

}