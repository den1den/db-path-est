package nl.tue.algorithm.histogram;

/**
 * Created by Dennis on 4-6-2016.
 */
public interface Joiner<Es> {
    Es joinLeft(Es subject, Es to);

    Es joinRight(Es to, Es subject);

    Es join(Es left, Es subject, Es right);
}
