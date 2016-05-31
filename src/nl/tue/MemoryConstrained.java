package nl.tue;

import nl.tue.io.Parser;

/**
 * Created by dennis on 25-5-16.
 */
public interface MemoryConstrained {

    /**
     * The current number of bytes used
     *
     * @return
     */
    long getBytesUsed();

}
