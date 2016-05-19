package nl.tue;

import junit.framework.TestCase;
import java.io.*;
import java.util.LinkedList;

/**
 * Created by dennis on 19-5-16.
 */
public class IOTest extends TestCase {
    private static File testFolder = new File(System.getenv("testFolder"));
    public void testSimple() throws Exception {
        String testFile = "biblio.txt";
        long maxLength = 5;
        long budget = 20000;
        long[][] inputs = new long[][]{
                new long[]{0, 0, 0}
        };
        timeMain(testFile, maxLength, budget, inputs);
    }

    /**
     * Times the main method
     *
     * @param testFile
     * @param maxLength
     * @param budget
     * @param inputs
     * @throws Exception
     */
    void timeMain(String testFile, long maxLength, long budget, long[][] inputs) throws Exception {
        final String inputsS[] = new String[inputs.length];
        for (int n = 0; n < inputs.length; n++) {
            inputsS[n] = "";
            for (int i = 0; i < inputs[n].length; i++) {
                inputsS[n] += String.valueOf(inputs[n][i]);
                if (i < inputs[n].length - 1) {
                    inputsS[n] += " ";
                }
            }
        }

        final LinkedList<Long> timings = new LinkedList<>();

        InputStream in = new InputStream() {
            int input = 0;
            StringReader current = null;

            private StringReader nextLine() {
                timings.add(System.currentTimeMillis());
                return new StringReader(inputsS[input++] + System.lineSeparator());
            }

            int doRead() throws IOException {
                synchronized (current) {
                    if (current == null) {
                        // first time
                        current = nextLine();
                    }

                    int read = current.read();
                    if (read == -1) {
                        // string ends
                        current = nextLine();
                        read = current.read();
                    }
                    return read;
                }
            }
            @Override
            public int read() throws IOException {
                if (input >= inputsS.length) {
                    // End of stream

                    synchronized (timings) {
                        timings.add(System.currentTimeMillis());
                        timings.notify();
                    }
                }
                return doRead();
            }
        };
        System.setIn(in);
        long t0 = System.currentTimeMillis();
        Main.main(new String[]{
                new File(testFolder, testFile).toString(),
                String.valueOf(maxLength),
                String.valueOf(budget)
        });
        long t1 = System.currentTimeMillis();

        System.out.println(String.format("Time to construct OG: %sms", t1 - t0));

        synchronized (timings) {
            timings.wait();
            long t_0 = t1;
            for (int i = 0; i < timings.size(); i++) {
                long t_1 = timings.get(i);
                System.out.println(String.format("Query: %sms for %s", t_1 - t_0, inputsS[i]));
                t_0 = t_1;
            }
        }
    }

}
