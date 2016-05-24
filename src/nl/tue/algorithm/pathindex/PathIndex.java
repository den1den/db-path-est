package nl.tue.algorithm.pathindex;

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

    public int[] getPathAsArray() {
        String[] splitPath = this.path.split("/");

        int[] pathArray = new int[splitPath.length];

        for(int i = 0; i < splitPath.length; i++) {
            pathArray[i] = Integer.parseInt(splitPath[i]);
        }

        return pathArray;
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
