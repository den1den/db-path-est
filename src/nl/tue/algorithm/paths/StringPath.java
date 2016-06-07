package nl.tue.algorithm.paths;

/**
 * Created by dennis on 2-6-16.
 */
public class StringPath {
    private String s;

    public StringPath(int[] path) {
        StringBuilder stringBuilder = new StringBuilder(path.length);
        for (int i = 0; i < path.length; i++) {
            char c = (char) path[i];
            stringBuilder.append(c);
        }
        this.s = stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringPath path = (StringPath) o;

        return s != null ? s.equals(path.s) : path.s == null;

    }

    @Override
    public int hashCode() {
        return s != null ? s.hashCode() : 0;
    }

    public int[] getPath() {
        int[] path = new int[s.length()];
        for (int i = 0; i < path.length; i++) {
            char c = this.s.charAt(i);
            path[i] = c;
        }
        return path;
    }
}
