package nl.tue.algorithm.pathindex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathan on 5/24/2016.
 */
public final class PathIndex {
    private final String path;

    public PathIndex(String path) {
        this.path = path;
    }

    public PathIndex(int[] path) {
        String out = "";

        for(int element : path) {
            out = out + element + "/";
        }

        this.path = out.substring(0, out.length() - 1);
    }

    public String getPath() {
        return path;
    }

    public int[] getPathAsIntArray() {
        String[] splitPath = this.path.split("/");

        int[] pathArray = new int[splitPath.length];

        for(int i = 0; i < splitPath.length; i++) {
            pathArray[i] = Integer.parseInt(splitPath[i]);
        }

        return pathArray;
    }

    public List<Long> getPathAsLongList() {
        List<Long> out = new ArrayList<>();

        for(int i : getPathAsIntArray()) {
            out.add((long)i);
        }

        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PathIndex pathIndex = (PathIndex) o;

        return path.equals(pathIndex.path);

    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
