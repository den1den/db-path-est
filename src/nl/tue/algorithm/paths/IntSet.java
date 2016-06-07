package nl.tue.algorithm.paths;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Created by dennis on 2-6-16.
 */
public class IntSet extends AbstractSet<Integer> {
    static final int BAG_SIZE = Integer.SIZE;
    protected int[] bags = new int[0];
    private int size = 0;

    private static int get(int bagIndex, int bitIndex) {
        return bagIndex * BAG_SIZE + bitIndex;
    }

    static String toString(int bag) {
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(bag));
        sb.reverse();
        while (sb.length() < BAG_SIZE) {
            sb.append('0');
        }
        return sb.toString();
    }

    public boolean addInt(int integer) {
        int bagIndex = integer / BAG_SIZE;
        int bitIndex = integer % BAG_SIZE;
        if (bagIndex >= bags.length) {
            setCap((int) ((bagIndex + 1) * 1.5));
        }
        int bag = bags[bagIndex];
        int selector = (1 << bitIndex);
        boolean val = ((bag & selector) != 0);
        bags[bagIndex] |= selector;
        size++;
        return !val;
    }

    @Override
    public boolean add(Integer integer) {
        if (integer < 0) {
            throw new IllegalArgumentException();
        }
        int bagIndex = integer / BAG_SIZE;
        if (bagIndex >= bags.length) {
            setCap((int) ((bagIndex + 1) * 1.5));
        }
        int bitIndex = integer % BAG_SIZE;
        int selector = (1 << bitIndex);

        boolean wasSet = isSet(bagIndex, bitIndex);

        bags[bagIndex] |= selector;

        size++;

        return !wasSet;
    }

    private void setCap(int n) {
        this.bags = Arrays.copyOf(bags, n);
    }

    @Override
    public java.util.Iterator<Integer> iterator() {
        return new Iterator();
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
        return (bags[bagIndex] & 1 << bitIndex) != 0;
    }

    public void join(IntSet ps) {
        if (this.bags.length < ps.bags.length) {
            this.setCap(ps.bags.length);
        }
        for (int i = 0; i < ps.bags.length; i++) {
            bags[i] |= ps.bags[i];
        }
        this.size = calcSize();
    }

    public void intersect(IntSet ps) {
        int min = Math.min(bags.length, ps.bags.length);
        for (int i = 0; i < min; i++) {
            bags[i] &= ps.bags[i];
        }
        this.size = calcSize();
    }

    public void setMinus(IntSet ps) {
        int min = Math.min(bags.length, ps.bags.length);
        for (int i = 0; i < min; i++) {
            int and = bags[i] & ps.bags[i];
            bags[i] ^= and;
        }
        this.size = calcSize();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.bags.length * BAG_SIZE);
        sb.append(getClass().getSimpleName()).append('(');
        for (int index = 0; index < bags.length; index++) {
            sb.append(toString(bags[index]));
            if (index < bags.length - 1) {
                sb.append('.');
            }
        }
        return sb.append(')').toString();
    }

    @Override
    public void clear() {
        setCap(0);
        size = 0;
    }

    class Iterator implements java.util.Iterator<Integer> {
        final int MAX = bags.length * BAG_SIZE;
        int index = 0;

        @Override
        public boolean hasNext() {
            // Check if at a zero
            do {
                if (index >= MAX) {
                    return false;
                }
                int currentBagIndex = index / BAG_SIZE;
                int currentBitIndex = index % BAG_SIZE;

                if (isSet(currentBagIndex, currentBitIndex)) {
                    break;
                }
                index++;
            } while (true);

            return true;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int currentBagIndex = this.index / BAG_SIZE;
            int currentBitIndex = this.index % BAG_SIZE;
            this.index++;
            return get(currentBagIndex, currentBitIndex);
        }
    }
}
