package nl.tue.algorithm.paths;

import java.util.AbstractSet;
import java.util.Arrays;

/**
 * Created by Dennis on 7-6-2016.
 */
public class PathSet extends AbstractSet<int[]> {
    final PathsOrdering pathsOrdering;
    IntSet intSet = new IntSet();

    public PathSet(PathsOrdering pathsOrdering) {
        this.pathsOrdering = pathsOrdering;
    }

    @Override
    public void clear() {
        intSet.clear();
    }

    @Override
    public java.util.Iterator<int[]> iterator() {
        final IntSet.Iterator iterator = intSet.new Iterator();
        return new java.util.Iterator<int[]>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public int[] next() {
                int i = iterator.next();
                return pathsOrdering.get(i);
            }
        };
    }

    @Override
    public int size() {
        return intSet.size();
    }

    @Override
    public boolean contains(Object o) {
        int[] i = (int[]) o;
        return intSet.contains(pathsOrdering.get(i));
    }

    @Override
    public boolean add(int[] ints) {
        int i = pathsOrdering.get(ints);
        return intSet.add(i);
    }

    public void join(PathSet ls) {
        assert pathsOrdering.getLabels() == ls.pathsOrdering.getLabels();
        this.intSet.join(ls.intSet);
    }

    public void intersect(PathSet ls) {
        assert pathsOrdering.getLabels() == ls.pathsOrdering.getLabels();
        this.intSet.intersect(ls.intSet);
    }

    public void setMinus(PathSet ls) {
        assert pathsOrdering.getLabels() == ls.pathsOrdering.getLabels();
        this.intSet.setMinus(ls.intSet);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(intSet.bags.length * IntSet.BAG_SIZE);
        sb.append(getClass().getSimpleName()).append('(');
        for (java.util.Iterator<int[]> iterator = this.iterator(); iterator.hasNext(); ) {
            int[] i = iterator.next();
            sb.append(Arrays.toString(i));
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append(')').toString();
    }
}
