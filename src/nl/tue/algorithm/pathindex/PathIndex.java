package nl.tue.algorithm.pathindex;

/**
 * Created by Nathan on 5/24/2016.
 */
public final class PathIndex {
    private final String path;
    private final int length;

    public PathIndex(String path) {
        this.path = path;
        length = path.split("/").length;
    }

    public PathIndex(int[] path) {
        String out = "";
        length = path.length;

        for(int element : path) {
            out = out + element + "/";
        }

        this.path = out.substring(0, out.length() - 1);
    }

    public PathIndex(PathIndex left, PathIndex right) {
        this.path = left.path + "/" + right.path;
        this.length = left.length + right.length;
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

    public int getLength() {
        return this.length;
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
