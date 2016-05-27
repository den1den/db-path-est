package nl.tue.io;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Nathan on 5/27/2016.
 */
public class ParserTest {

    @Rule
    public TemporaryFolder folder  = new TemporaryFolder();

    File file;

    @Before
    public void before() throws IOException {
        file = folder.newFile();
    }

    @Test
    public void testSimpleMappingIsRecorded() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 0 2");
        out.flush();

        out.close();

        Parser p = new Parser();

        p.parse(file);

        Assert.assertEquals(2, p.getEdgeMappings().size());
        Assert.assertTrue(0 == p.getEdgeMappings().get("+0"));
        Assert.assertTrue(1 == p.getEdgeMappings().get("-0"));
    }

    @Test
    public void testMultiLabelMappingIsRecorded() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 0 2");
        out.println("3 1 3");
        out.println("4 2 3");
        out.flush();

        out.close();

        Parser p = new Parser();

        p.parse(file);

        Assert.assertEquals(6, p.getEdgeMappings().size());
        Assert.assertTrue(0 == p.getEdgeMappings().get("+0"));
        Assert.assertTrue(3 == p.getEdgeMappings().get("-0"));

        Assert.assertTrue(1 == p.getEdgeMappings().get("+1"));
        Assert.assertTrue(4 == p.getEdgeMappings().get("-1"));

        Assert.assertTrue(2 == p.getEdgeMappings().get("+2"));
        Assert.assertTrue(5 == p.getEdgeMappings().get("-2"));
    }

    @Test
    public void testMultiLabelMapWithGaps() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 0 2");
        out.println("3 4 3");
        out.println("4 5 3");
        out.flush();

        out.close();

        Parser p = new Parser();

        p.parse(file);

        Assert.assertEquals(6, p.getEdgeMappings().size());
        Assert.assertTrue(0 == p.getEdgeMappings().get("+0"));
        Assert.assertTrue(3 == p.getEdgeMappings().get("-0"));

        Assert.assertTrue(1 == p.getEdgeMappings().get("+4"));
        Assert.assertTrue(4 == p.getEdgeMappings().get("-4"));

        Assert.assertTrue(2 == p.getEdgeMappings().get("+5"));
        Assert.assertTrue(5 == p.getEdgeMappings().get("-5"));
    }

    @Test
    public void testMultiLabelMapWithGapsAndNonLinearOrder() throws IOException {
        PrintWriter out = new PrintWriter(file);

        out.println("1 4 2");
        out.println("3 0 3");
        out.println("4 5 3");
        out.flush();

        out.close();

        Parser p = new Parser();

        p.parse(file);

        Assert.assertEquals(6, p.getEdgeMappings().size());
        Assert.assertTrue(0 == p.getEdgeMappings().get("+4"));
        Assert.assertTrue(3 == p.getEdgeMappings().get("-4"));

        Assert.assertTrue(1 == p.getEdgeMappings().get("+0"));
        Assert.assertTrue(4 == p.getEdgeMappings().get("-0"));

        Assert.assertTrue(2 == p.getEdgeMappings().get("+5"));
        Assert.assertTrue(5 == p.getEdgeMappings().get("-5"));
    }
}
