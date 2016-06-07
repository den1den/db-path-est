package nl.tue.algorithm.histogram;

/**
 * Created by Dennis on 7-6-2016.
 */
class HistogramRange<Es> implements Comparable<HistogramRange> {
    Es estimation;
    int startIndex;
    int endIndex;

    HistogramRange(Es estimation, int startIndex, int endIndex) {
        this.estimation = estimation;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public int compareTo(HistogramRange o) {
        return Integer.compare(startIndex, o.startIndex);
    }

    boolean has(int index) {
        return startIndex <= index && index <= endIndex;
    }

    int size() {
        return endIndex - startIndex + 1;
    }
}
