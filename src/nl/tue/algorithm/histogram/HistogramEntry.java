package nl.tue.algorithm.histogram;

/**
 * Created by Dennis on 15-6-2016.
 */
public class HistogramEntry {
    public int low, high;
    public short value;

    public HistogramEntry(int index, short value) {
        this(index, index + 1, value);
    }

    public HistogramEntry(int low, int high, short value) {
        this.low = low;
        this.high = high;
        this.value = value;
        assert high > low;
    }

    public int length() {
        return high - low;
    }
}
