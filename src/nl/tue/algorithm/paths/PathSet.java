package nl.tue.algorithm.paths;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by dennis on 2-6-16.
 */
public class PathSet extends AbstractSet<int[]> {
    private LabelSequence sequence;
    private int[] bags = new int[0];
    private int size = 0;
    static final int BAG_SIZE = Integer.SIZE;

    public PathSet(LabelSequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean add(int[] path) {
        int pi = sequence.get(path);
        return add(pi);
    }

    private boolean add(int index) {
        int bagIndex = index / BAG_SIZE;
        if (bagIndex >= bags.length) {
            setCap((int) ((bagIndex + 1) * 1.5));
        }
        int bitIndex = index % BAG_SIZE;
        int selector = (1 << bitIndex);

        boolean wasSet = (bags[bagIndex] & selector) > 0;

        bags[bagIndex] |= selector;

        size++;

        return !wasSet;
    }

    private static int getIndex(int bagIndex, int bitIndex) {
        return bagIndex * BAG_SIZE + bitIndex;
    }

    private void setCap(int n) {
        this.bags = Arrays.copyOf(bags, n);
    }

    @Override
    public Iterator<int[]> iterator() {
        return new SIt();
    }

    @Override
    public int size() {
        return size;
    }

    private int calcSize() {
        int size = 0;
        for (int i = 0; i < bags.length; i++) {
            size += Integer.bitCount(bags[i]);
        }
        return size;
    }

    private boolean isSet(int bagIndex, int bitIndex) {
        return (bags[bagIndex] & 1 << bitIndex) > 0;
    }

    public void join(PathSet ps) {
        assert sequence.getLabels() == ps.sequence.getLabels();
        if (this.bags.length < ps.bags.length) {
            this.setCap(ps.bags.length);
        }
        for (int i = 0; i < ps.bags.length; i++) {
            bags[i] |= ps.bags[i];
        }
        this.size = calcSize();
    }

    public void intersect(PathSet ps) {
        int min = Math.min(bags.length, ps.bags.length);
        for (int i = 0; i < min; i++) {
            bags[i] &= ps.bags[i];
        }
        this.size = calcSize();
    }

    public void setMinus(PathSet ps) {
        int min = Math.min(bags.length, ps.bags.length);
        for (int i = 0; i < min; i++) {
            int and = bags[i] & ps.bags[i];
            bags[i] ^= and;
        }
        this.size = calcSize();
    }

    private class SIt implements Iterator<int[]> {
        int currentbagIndex = 0;
        int currentBitIndex = 0;

        @Override
        public boolean hasNext() {
            // Check if at a zero
            if (currentbagIndex >= bags.length) {
                return false;
            }
            int selector = 1 << currentBitIndex;
            while ((bags[currentbagIndex] & selector) == 0) {
                if (currentBitIndex == BAG_SIZE - 1) {
                    if (currentbagIndex == bags.length - 1) {
                        currentbagIndex = bags.length;
                        return false;
                    }
                    currentbagIndex++;
                    currentBitIndex = 0;
                } else {
                    currentBitIndex++;
                }
                selector = 1 << currentBitIndex;
            }
            return true;
        }

        @Override
        public int[] next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int index = getIndex(currentbagIndex, currentBitIndex);
            currentBitIndex++;
            return sequence.get(index);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.bags.length * BAG_SIZE);
        sb.append(getClass().getSimpleName()).append('(');
        for (int index = 0; index < bags.length; index++) {
            sb.append(Integer.toBinaryString(bags[index]));
        }
        return sb.append(')').toString();
    }

    public String toFullString() {
        StringBuilder sb = new StringBuilder(this.bags.length * BAG_SIZE);
        sb.append(getClass().getSimpleName()).append('(');
        for (Iterator<int[]> iterator = this.iterator(); iterator.hasNext(); ) {
            int[] i = iterator.next();
            sb.append(Arrays.toString(i));
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append(')').toString();
    }
}
