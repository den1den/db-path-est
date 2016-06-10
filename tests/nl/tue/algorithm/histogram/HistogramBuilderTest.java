package nl.tue.algorithm.histogram;//import static org.junit.Assert.*;

import junit.framework.TestCase;
import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.paths.PathsOrdering;
import nl.tue.algorithm.paths.PathsOrderingLexicographical;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Dennis on 7-6-2016.
 */
public class HistogramBuilderTest extends TestCase {
    static private final PathsOrdering pathsOrdering = new PathsOrderingLexicographical(15, 5);
}