package nl.tue.algorithm.histogram;

import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.paths.PathsOrdering;
import nl.tue.algorithm.paths.PathsIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public abstract class AbstractHistogramBuilder<E, H extends AbstractHistogram<E>> {

    PathsOrdering pathsOrdering;
    private TreeSet<HistogramRange<E>> ranges;

    public AbstractHistogramBuilder() {
    }

    private HistogramRange<E> lower, higher;
    private HistogramRange<E> prepareInsert(int newIndex){
        HistogramRange<E> newRange = new HistogramRange<>(null, newIndex, newIndex);
        HistogramRange<E> floor = ranges.floor(newRange);

        assert floor != null;
        assert floor.estimation == null; // Assume not already occupied
        assert floor.has(newIndex);

        if(newIndex == floor.startIndex){
            // Move floor to right
            ranges.remove(floor);
            floor.startIndex++;
            if(floor.startIndex <= floor.endIndex){
                ranges.add(floor);
                higher = floor;
            } else {
                higher = null;
            }

            lower = ranges.lower(floor);
        } else {
            assert floor.size() >= 2;
            if (newIndex == floor.endIndex) {
                // Move floor to left
                floor.endIndex--;
                assert floor.startIndex <= floor.endIndex;
                higher = ranges.higher(floor);

                lower = floor;
            } else {
                // In between, so split up
                assert floor.size() >= 3;
                assert floor.estimation == null;

                higher = new HistogramRange<>(null, newIndex + 1, floor.endIndex);
                ranges.add(higher);

                floor.endIndex = newIndex - 1;
                lower = floor;
            }
        }

        return newRange;
    }

    TreeSet<HistogramRange<E>> buildRanges(Estimator<E> graph, Joiner<E> joiner, PathsIterator pathsIterator) {
        ranges = new TreeSet<>();
        int MAX = Integer.MAX_VALUE;
        ranges.add(new HistogramRange<>(null, 0, MAX));

        while (pathsIterator.hasNext()) {
            int[] path = pathsIterator.next();

            // Create new range tuple
            int index = pathsOrdering.get(path);
            HistogramRange<E> newR = prepareInsert(index);

            // Create estimation
            E estimation = graph.getEstimation(path);

            int leftTuples;
            E leftEstimate;
            if (lower != null) {
                leftTuples = lower.size();
                leftEstimate = lower.estimation;
            } else {
                leftTuples = 0;
                leftEstimate = null;
            }

            int rightTuples;
            E rightEstimate;
            if (higher != null) {
                rightTuples = higher.size();
                rightEstimate = higher.estimation;
            } else {
                rightTuples = 0;
                rightEstimate = null;
            }

            E newEstimation = joiner.calcJoin(leftTuples, leftEstimate, estimation, rightTuples, rightEstimate);
            if(joiner.isJoinLeft() && joiner.isJoinRight()){
                if(higher != null) {
                    ranges.remove(higher);
                    lower.endIndex = higher.endIndex;
                } else {
                    lower.endIndex = MAX;
                }
                lower.estimation = newEstimation;
            } else if (joiner.isJoinLeft()){
                lower.endIndex = index;
                lower.estimation = newEstimation;
            } else if (joiner.isJoinRight()){
                if(higher != null){
                    ranges.remove(higher);
                    higher.startIndex = index;
                    higher.estimation = newEstimation;
                } else {
                    // not possible?
                    higher = new HistogramRange<>(estimation, index, MAX);
                }
                ranges.add(higher);
            } else {
                newR.estimation = newEstimation;
                ranges.add(newR);
            }
        }
        return ranges;
    }

    public H build(PathsOrdering pathsOrdering, Estimator<E> graph, Joiner<E> joiner, PathsIterator pathsIterator) {
        this.pathsOrdering = pathsOrdering;
        TreeSet<HistogramRange<E>> ranges = buildRanges(graph, joiner, pathsIterator);
        check(ranges);

        ArrayList<Integer> startRanges = new ArrayList<>(ranges.size() - 1);
        E[] estimations = createArray(ranges.size());

        int ix = 0;
        Iterator<HistogramRange<E>> i = ranges.iterator();
        HistogramRange<E> a = i.next();
        estimations[ix] = a.estimation;

        ix++;
        while (i.hasNext()) {
            a = i.next();
            estimations[ix] = a.estimation;
            startRanges.add(a.startIndex);

            ix++;
        }

        return createH(pathsOrdering, startRanges, estimations);
    }

    private void check(TreeSet<HistogramRange<E>> ranges) {
        Iterator<HistogramRange<E>> i = ranges.iterator();
        assert i.hasNext();

        HistogramRange<E> a = i.next();
        assert a.startIndex == 0;
        boolean lastWasEmty = a.estimation == null;

        while (i.hasNext()) {
            HistogramRange<E> b = i.next();
            assert a.endIndex + 1 == b.startIndex;
            if (lastWasEmty) {
                assert b.estimation != null;
            }
            lastWasEmty = b.estimation == null;

            a = b;
        }
    }

    protected abstract H createH(PathsOrdering pathsOrdering, ArrayList<Integer> startRanges, E[] estimations);

    protected abstract E[] createArray(int length);

}