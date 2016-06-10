package nl.tue.algorithm.subgraph;

import com.sun.deploy.util.ArrayUtil;
import nl.tue.io.TupleList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipInputStream;

/**
 * Created by Nathan on 6/5/2016.
 */
public class SubgraphCompressor {

    private static final int INT_LENGTH = 3;

    private static final int EDGE_LENGTH = 7;

    public static byte[] compressEdge(int[] edge) {
        byte[] out = new byte[7];

        byte[] startNode = intToThreeBytes(edge[0]);
        byte label = (byte) edge[1];
        byte[] endNode = intToThreeBytes(edge[2]);

        out[0] = startNode[0];
        out[1] = startNode[1];
        out[2] = startNode[2];
        out[3] = label;
        out[4] = endNode[0];
        out[5] = endNode[1];
        out[6] = endNode[2];

        return out;
    }

    public static int[] decompressEdge(byte[] edge) {
        if (edge.length < EDGE_LENGTH) {
            throw new IllegalArgumentException("Invalid edge length");
        }

        return new int[]{threeBytesToInt(new byte[]{edge[0], edge[1], edge[2]}), ((int) edge[3] & 0xff),
                threeBytesToInt(new byte[]{edge[4], edge[5], edge[6]})};
    }


    public static byte[] intToThreeBytes(int integer) {
        byte b1, b2, b3;

        b1 = (byte) (integer >>> 16);
        b2 = (byte) (integer >>> 8);
        b3 = (byte) (integer);

        return new byte[]{b1, b2, b3};
    }

    public static int threeBytesToInt(byte[] bytes) {
        if (bytes.length < INT_LENGTH) {
            throw new IllegalArgumentException("Byte array is too small");
        }

        return (((int) bytes[0] & 0xff) << 16) | (((int) bytes[1] & 0xff) << 8) | ((int) bytes[2] & 0xff);
    }

    public static byte[] compress(byte[] input) {
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);

        compressor.setInput(input);
        compressor.finish();

        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        try {
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       return bos.toByteArray();
    }

    public static byte[] decompress(byte[] input) {
        Inflater decompressor = new Inflater();
        decompressor.setInput(input);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            int count = 0;
            try {
                count = decompressor.inflate(buf);
            } catch (DataFormatException e) {
                throw new RuntimeException(e);
            }
            bos.write(buf, 0, count);

        }
        try {
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bos.toByteArray();
    }

    public static TupleList decompressSubgraph(byte[] compressed) {
        byte[] decompressed = decompress(compressed);

        TupleList out = new TupleList();

        for(int i = 0; i < decompressed.length; i+=EDGE_LENGTH) {
            byte[] edge = new byte[EDGE_LENGTH];

            System.arraycopy(decompressed, i, edge, 0, EDGE_LENGTH);

            out.add(decompressEdge(edge));
        }

        return out;
    }

    public static byte[] compressWithLimit(Iterable<int[]> provider, int b) {
        int initialEdgeCount = b / EDGE_LENGTH;

        byte[] initial = new byte[initialEdgeCount * 7];

        int added = 0;

        for(int[] edge : provider) {
            System.arraycopy(compressEdge(edge), 0, initial, added*7, 7);
            added++;

            if(added == initialEdgeCount) {
                byte[] compressed = compress(initial);

                if(compressed.length < b) {

                    double rateOfCompression = (double) initial.length / (double) compressed.length;

                    int left = (int) (((b - compressed.length) / EDGE_LENGTH) * rateOfCompression);

                    initialEdgeCount += left;

                    byte[] newInitial = new byte[initialEdgeCount*7];

                    System.arraycopy(initial, 0, newInitial, 0, initial.length);

                    initial = newInitial;
                }
            }
        }

        byte[] out = new byte[added*EDGE_LENGTH];

        /**
         * Remove empty tail of array.
         */
        System.arraycopy(initial, 0, out, 0, out.length);

        return compress(out);
    }

    public static byte[] doublesToByteArray(List<Double> doubles) {
        byte[] out = new byte[doubles.size() * 8];

        for(int i = 0; i < doubles.size(); i++) {
            ByteBuffer.wrap(out, i*8, 8).putDouble(doubles.get(i));
        }

        return out;
    }

    public static List<Double> byteArrayToDoubles(byte[] bytes) {
        List<Double> out = new ArrayList<>();

        int doubles = bytes.length / 8;

        for(int i = 0; i < doubles; i++) {
            out.add(ByteBuffer.wrap(bytes, i*8, 8).getDouble());
        }

        return out;
    }



}
