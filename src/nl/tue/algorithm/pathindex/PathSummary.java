package nl.tue.algorithm.pathindex;

/**
 * Created by Nathan on 5/24/2016.
 */
public class PathSummary {

    private final PathIndex index;
    private final Summary summary;

    public PathSummary(PathIndex index, Summary summary) {
        this.index = index;
        this.summary = summary;
    }

    public PathIndex getIndex() {
        return index;
    }

    public Summary getSummary() {
        return summary;
    }
}
