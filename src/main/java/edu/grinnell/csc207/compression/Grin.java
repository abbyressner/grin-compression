package edu.grinnell.csc207.compression;

import java.io.IOException;
import java.util.HashMap;
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
    public static void decode(String infile, String outfile) throws IOException {
        BitInputStream in = new BitInputStream(infile);
        BitOutputStream out = new BitOutputStream(outfile);
        if (in.readBits(32) == 0x736) {
            HuffmanTree ht = new HuffmanTree(in);
            ht.decode(in, out);
        } else {
            throw new IllegalArgumentException();
        }
        in.close();
        out.close();
    }

    /**
     * Creates a mapping from 8-bit sequences to number-of-occurrences of those
     * sequences in the given file. To do this, read the file using a
     * BitInputStream, consuming 8 bits at a time.
     *
     * @param file the file to read
     * @return a freqency map for the given file
     */
    public static Map<Short, Integer> createFrequencyMap(String file) throws IOException {
        BitInputStream in = new BitInputStream(file);
        Map<Short, Integer> freqs = new HashMap<>();
        while (in.hasBits()) {
            short value = (short) in.readBits(8);
            if (freqs.containsKey(value)) {
                freqs.put(value, freqs.get(value) + 1);
            } else {
                freqs.put(value, 1);
            }
        }
        in.close();
        return freqs;
    }

    /**
     * Encodes the given file denoted by infile and writes the output to the
     * .grin file denoted by outfile.
     *
     * @param infile the file to encode.
     * @param outfile the file to write the output to.
     */
    public static void encode(String infile, String outfile) throws IOException {
        Map<Short, Integer> freqs = createFrequencyMap(infile);
        BitInputStream in = new BitInputStream(infile);
        BitOutputStream out = new BitOutputStream(outfile);
        HuffmanTree ht = new HuffmanTree(freqs);
        out.writeBits(0x736, 32);
        ht.serialize(out);
        ht.encode(in, out);
        in.close();
        out.close();
    }

    /**
     * The entry point to the program.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 3 || (!args[0].equals("encode") && !args[0].equals("decode"))) {
            System.out.println("Usage: java Grin <encode|decode> <infile> <outfile>");
            System.exit(0);
        } 

        String infile = args[1];
        String outfile = args[2];

        if (args[0].equals("encode")) {
            encode(infile, outfile);
        }

        if (args[0].equals("decode")) {
            decode(infile, outfile);
        }
    }
}
