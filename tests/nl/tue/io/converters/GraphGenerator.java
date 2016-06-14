package nl.tue.io.converters;//import static org.junit.Assert.*;

import nl.tue.comparison.ComparisonExecutor;
import nl.tue.io.TupleList;
import org.junit.Test;

import java.io.File;

/**
 * Writes smaller test cases to folder: tests\resources\[biblio cineast AND music]
 * running time: 3 seconds
 */
public class GraphGenerator {
    @Test
    public void testGenerateCineasts() throws Exception {
        File f = ComparisonExecutor.cineastFile;
        generate(f, "cineast");
    }

    @Test
    public void testGenerateBiblio() throws Exception {
        File f = ComparisonExecutor.biblioFile;
        generate(f, "biblio");
    }

    @Test
    public void testGenerateMusic() throws Exception {
        File f = ComparisonExecutor.musicFile;
        generate(f, "music");
    }

    private static File BASEFOLDER = new File("tests\\resources");

    private void generate(File file, String set) {
        File folder = new File(BASEFOLDER, set);
        if(!folder.exists())
            folder.mkdirs();
        int LABELSTEP = 2;

        TupleList original = new TupleList(file);

        for (double nodesFactor = 0.2; nodesFactor <= 1; nodesFactor += 0.2) {
            NodeConverter nodeConverter = new NodeConverter(original);
            TupleList filteredNodes = nodeConverter.filterNodes(nodesFactor);
            if(filteredNodes.size() == 0){
                System.out.println("nodesFactor "+nodesFactor+" leads to 0 edges, skipped");
            } else {
                TupleList outputGraph = filteredNodes;
                TupleList.Meta meta = outputGraph.new Meta();
                int labels = meta.labels.size();
                int nodes = meta.nodes.size();
                do{
                    write(set, outputGraph, labels, nodes);

                    labels /= LABELSTEP;
                    if(labels > 0) {
                        LabelConverter labelConverter = new LabelConverter(filteredNodes);
                        outputGraph = labelConverter.devide(LABELSTEP);
                    } else {
                        break;
                    }
                }while (true);
            }
        }
    }

    private static void write(String set, TupleList outputGraph, int labels, int nodes) {
        File output = getGenerated(set, labels, nodes);
        if(output.exists()){
            System.err.println("File already exists: "+output);
        } else {
            outputGraph.writeToCSVFile(output);
            System.out.println("Written to " + output);
        }
    }

    public static File getGenerated(String set, int labels, int nodes){
        File folder = new File(BASEFOLDER, set);
        String outputFilename = String.format("%s_%s_%s.csv", set, nodes, labels);
        File file = new File(folder, outputFilename);
        return file;
    }
}