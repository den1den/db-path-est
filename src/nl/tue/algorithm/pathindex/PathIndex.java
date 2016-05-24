package nl.tue.algorithm.pathindex;

/**
 * Created by Nathan on 5/24/2016.
 */
public final class PathIndex {
    private final String path;

    public PathIndex(String path) {
        this.path = path;
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
