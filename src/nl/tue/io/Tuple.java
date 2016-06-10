package nl.tue.io;

/**
 * Replaced someDS
 */
public class Tuple {
    public int source;
    public int label;
    public int target;

    public Tuple(int source, int label, int target) {
        this.source = source;
        this.label = label;
        this.target = target;
    }

    public Tuple(int[] ints) {
        assert ints.length == 3;
        this.source = ints[0];
        this.label = ints[1];
        this.target = ints[2];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple tuple = (Tuple) o;

        if (source != tuple.source) return false;
        if (label != tuple.label) return false;
        return target == tuple.target;

    }

    @Override
    public int hashCode() {
        int result = source;
        result = 31 * result + label;
        result = 31 * result + target;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Tuple(%d, %d, %d)", source, label, target);
    }
}
