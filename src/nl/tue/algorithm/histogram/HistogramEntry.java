package nl.tue.algorithm.histogram;

/**
 * Created by Dennis on 15-6-2016.
 */
public class HistogramEntry {
    public int low, high;
    public short value;

    public HistogramEntry(int index, short value) {
        this(index, index, value);
    }

    public HistogramEntry(int low, int high, short value) {
        this.low = low;
        this.high = high;
        this.value = value;
    }

    public int length() {
        return high - low + 1;
    }
}
