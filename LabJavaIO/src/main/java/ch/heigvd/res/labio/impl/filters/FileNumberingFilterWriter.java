package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    private boolean start = true;
    private int counter = 1;
    private boolean cloche = false;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(str.charAt(i));
        }

    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(cbuf[i]);
        }
    }

    @Override
    public void write(int c) throws IOException {

        if (start) {
            super.write(counter++ + "\t", 0, 2);
            start = false;
        }

        if (cloche) {
            if (c != '\r') {
                cloche = false;
            }
            if (c == '\n') {
                super.write("\r\n" + counter + "\t", 0, 3 + String.valueOf(counter++).length());

            } else {
                super.write("\r" + counter + "\t" + (char) c, 0, 3 + String.valueOf(counter++).length());
            }
        } else {
            if (c == '\r') {
                cloche = true;
            } else if (c == '\n') {
                super.write("\n" + counter + "\t", 0, 2 + String.valueOf(counter++).length());
            } else {
                super.write(c);
            }
        }

    }

}
