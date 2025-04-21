package edu.grinnell.csc207.compression;

import java.util.Map;

/**
 * The driver for the Grin compression program.
 */
public class Grin {

    /**
     * Decodes the .grin file denoted by infile and writes the output to the
     * .grin file denoted by outfile.
     *
     * @param infile the file to decode
     * @param outfile the file to ouptut to
     */
    public static void decode(String infile, String outfile) {
        // TODO: fill me in!
    }

    /**
     * Creates a mapping from 8-bit sequences to number-of-occurrences of those
     * sequences in the given file. To do this, read the file using a
     * BitInputStream, consuming 8 bits at a time.
     *
     * @param file the file to read
     * @return a freqency map for the given file
     */
    public static Map<Short, Integer> createFrequencyMap(String file) {
        // TODO: fill me in!
        return null;
    }

    /**
     * Encodes the given file denoted by infile and writes the output to the
     * .grin file denoted by outfile.
     *
     * @param infile the file to encode.
     * @param outfile the file to write the output to.
     */
    public static void encode(String infile, String outfile) {
        // TODO: fill me in!
    }

    /**
     * The entry point to the program.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        // TODO: fill me in!
        System.out.println("Usage: java Grin <encode|decode> <infile> <outfile>");
    }
}
