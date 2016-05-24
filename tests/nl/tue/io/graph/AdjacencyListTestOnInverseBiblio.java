package nl.tue.io.graph;

import nl.tue.io.Parser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;


public class AdjacencyListTestOnInverseBiblio {

    private static String testFolderS = System.getenv("testFolder");
    private static File file = new File(testFolderS,"biblio.txt");

    @Test
    public void testloadAndInverseFile() throws IOException {
        Parser p = new Parser();
        p.inverse(file);
        //check if first one is inversed
        Assert.assertEquals(p.invertedTuples.get(0)[0],626);
        Assert.assertEquals(p.invertedTuples.get(0)[1],0);
        Assert.assertEquals(p.invertedTuples.get(0)[2],3);
        //check if halfway are inverted
        Assert.assertEquals(p.invertedTuples.get(649)[0],229);
        Assert.assertEquals(p.invertedTuples.get(649)[1],-5);
        Assert.assertEquals(p.invertedTuples.get(649)[2],102);
        //check if all are in the invertedlist
        Assert.assertEquals(p.invertedTuples.size(),1499);

    }
 @Test
    public void MakeNewInvertedFile()  throws IOException
    {
        String newFileLocaton = "D:\\invertedFile.txt";
        Parser p = new Parser();
        try {
            p.parse(file);
            p.inverse(file);
            p.writeToFile(newFileLocaton);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int combinedSize = p.tuples.size() + p.invertedTuples.size();
        Assert.assertEquals(p.combinedList.size(),combinedSize);
    }



}
