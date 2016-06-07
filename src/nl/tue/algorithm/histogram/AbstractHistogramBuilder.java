package nl.tue.algorithm.histogram;

import nl.tue.algorithm.Estimator;
import nl.tue.algorithm.paths.LabelSequence;
import nl.tue.algorithm.paths.PathsIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public abstract class AbstractHistogramBuilder<E, H extends AbstractHistogram<E>> {

    private LabelSequence labelSequence;

    public AbstractHistogramBuilder() {
    }

    TreeSet<Range<E>> buildRanges(Estimator<E> graph, Joiner<E> joiner, PathsIterator pathsIterator) {
        TreeSet<Range<E>> ranges = new TreeSet<>();
        int MAX = Integer.MAX_VALUE;
        ranges.add(new Range<>(null, 0, MAX));

        while (pathsIterator.hasNext()) {
            int[] path = pathsIterator.next();

            // Create estimation
            E estimation = graph.getEstimation(path);


            // Create new range tuple
            int index = labelSequence.get(path);
            Range<E> candidateRange = new Range<>(estimation, index, index);

            // Get floor
            Range<E> floor;
            Range<E> hit = ranges.floor(candidateRange);
            assert hit != null;
            assert hit.estimation == null;

            if (index == 0) {
                floor = null;
            } else if (hit.has(index - 1)) {
                floor = null;
            } else {
                floor = ranges.lower(hit);
                assert floor != null;
                assert floor.endIndex == index - 1;
            }

            // Get ceiling
            Range<E> ceil;
            if (index == MAX) {
                ceil = null;
            } else if (hit.has(index + 1)) {
                ceil = null;
            } else {
                ceil = ranges.higher(hit);
                assert ceil != null;
                assert ceil.startIndex == index + 1;
            }

            E join;
            if (floor != null && ceil != null) {
                join = joiner.join(floor.estimation, estimation, ceil.estimation);
                if (join != null) {
                    // Replace left + subject + right
                    floor.estimation = join;
                    floor.endIndex = ceil.endIndex;
                    ranges.remove(ceil);
                }
            } else if (floor != null) {
                join = joiner.joinRight(floor.estimation, estimation);
                if (join != null) {
                    // Replace subject + right
                    floor.estimation = join;
                    floor.endIndex++;
                }
            } else if (ceil != null) {
                join = joiner.joinLeft(estimation, ceil.estimation);
                if (join != null) {
                    // Replace subject + left
                    ceil.estimation = join;
                    ceil.startIndex--;
                }
            } else {
                throw new RuntimeException();
            }
            if (join == null) {
                ranges.add(candidateRange);
            }
        }
        return ranges;
    }

    public H build(LabelSequence labelSequence, Estimator<E> graph, Joiner<E> joiner, PathsIterator pathsIterator) {
        this.labelSequence = labelSequence;
        TreeSet<Range<E>> ranges = buildRanges(graph, joiner, pathsIterator);
        test(ranges);

        ArrayList<Integer> startRanges = new ArrayList<>(ranges.size() - 1);
        E[] estimations = createArray(ranges.size());

        int ix = 0;
        Iterator<Range<E>> i = ranges.iterator();
        Range<E> a = i.next();
        estimations[ix] = a.estimation;

        ix++;
        while (i.hasNext()) {
            a = i.next();
            estimations[ix] = a.estimation;
            startRanges.add(a.startIndex);

            ix++;
        }

        return createH(labelSequence, startRanges, estimations);
    }

    private void test(TreeSet<Range<E>> ranges) {
        Iterator<Range<E>> i = ranges.iterator();
        assert i.hasNext();

        Range<E> a = i.next();
        assert a.startIndex == 0;
        boolean lastWasEmty = a.estimation == null;

        while (i.hasNext()) {
            Range<E> b = i.next();
            assert a.endIndex + 1 == b.startIndex;
            if (lastWasEmty) {
                assert b.estimation != null;
            }
            lastWasEmty = b.estimation == null;

            a = b;
        }
    }

    protected abstract H createH(LabelSequence labelSequence, ArrayList<Integer> startRanges, E[] estimations);

    protected abstract E[] createArray(int length);

    static class Range<Es> implements Comparable<Range> {
        Es estimation;
        int startIndex;
        int endIndex;

        private Range(Es estimation, int startIndex, int endIndex) {
            this.estimation = estimation;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public int compareTo(Range o) {
            return Integer.compare(startIndex, o.startIndex);
        }

        private boolean has(int index) {
            return startIndex <= index && index <= endIndex;
        }
    }
}