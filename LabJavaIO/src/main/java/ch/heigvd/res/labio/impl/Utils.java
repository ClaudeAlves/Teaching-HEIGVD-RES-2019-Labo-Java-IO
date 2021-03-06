package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    /**
     * This method looks for the next new line separators (\r, \n, \r\n) to extract
     * the next line in the string passed in arguments.
     *
     * @param lines a string that may contain 0, 1 or more lines
     * @return an array with 2 elements; the first element is the next line with
     * the line separator, the second element is the remaining text. If the argument does not
     * contain any line separator, then the first element is an empty string.
     */
    private static final String[] SEPARATORS = new String[]{"\r\n", "\n", "\r"};

    public static String[] getNextLine(String lines) {
        for (String s : SEPARATORS) {
            if (lines.contains(s)) {
                return new String[]{lines.substring(0, lines.indexOf(s) + s.length()), lines.substring(lines.indexOf(s) + s.length())};
            }
        }
        return new String[]{"", lines};
    }

}
