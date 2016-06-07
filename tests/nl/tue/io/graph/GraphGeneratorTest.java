package nl.tue.io.graph;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import nl.tue.io.Parser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by OttkO on 6/2/2016.
 */
public class GraphGeneratorTest {

    Parser p;
    String fileLocation = "D:\\test.txt";
    @Before
    public void before() throws IOException {
        p = new Parser();
    }
    @Test
    public void SaveGeneratedGraph() throws IOException {
        p.GenerateGraph(5,10,false);
        p.writeToFile(fileLocation);
    }
    @Test
    public void InvertGeneratedGraph() throws IOException {
       File newFile = new File(fileLocation);
        p.parse(newFile);
        p.inverse(newFile);
        p.writeToFile(fileLocation);
    }
    @Test
        public void WritePathToFile() throws IOException {
        p.writePathToFile("D:\\test.txt","4567890",2);
    }



}
