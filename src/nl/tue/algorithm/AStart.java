package nl.tue.algorithm;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dennis on 19-5-16.
 */
public class AStart implements Iterable<int[]> {

    private int[] labels;
    private int maxDepth;
    private HeuristicEvaluator heuristicEvaluator;

    public AStart(int[] labels, int maxDepth, HeuristicEvaluator heuristicEvaluator) {
        this.labels = labels;
        this.maxDepth = maxDepth;
        this.heuristicEvaluator = heuristicEvaluator;
    }

    @Override
    public Iterator<int[]> iterator() {
        return new AStartIterator();
    }

    public abstract class HeuristicEvaluator {
        private double heuristic = Double.NaN;

        public void setHeuristic(double heuristic) {
            this.heuristic = heuristic;
        }
    }

    private class AStartIterator implements Iterator<int[]> {
        Iterator<Integer> firstLevel;
        Map<Integer, Integer> heuristics;

        AStartIterator() {
            this.firstLevel = Arrays.stream(labels).iterator();
            this.heuristics = new HashMap<>();
        }

        int level = 0;

        @Override
        public boolean hasNext() {
            if (level == 0) {
                if (firstLevel.hasNext()) {

                }
            }
            throw new NotImplementedException();
        }

        @Override
        public int[] next() {
            throw new NotImplementedException();
        }
    }
}
