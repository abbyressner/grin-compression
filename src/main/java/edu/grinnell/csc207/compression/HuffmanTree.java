package edu.grinnell.csc207.compression;

import java.util.Map;

/**
 * A HuffmanTree derives a space-efficient coding of a collection of byte
 * values.
 *
 * The huffman tree encodes values in the range 0--255 which would normally take
 * 8 bits. However, we also need to encode a special EOF character to denote the
 * end of a .grin file. Thus, we need 9 bits to store each byte value. This is
 * fine for file writing (modulo the need to write in byte chunks to the file),
 * but Java does not have a 9-bit data type. Instead, we use the next larger
 * primitive integral type, short, to store our byte values.
 */
public class HuffmanTree {

    private static class Node {

        private short value;
        private int freq;
        private Node left;
        private Node right;

        public Node(short value, int freq) {
            this.value = value;
            this.freq = freq;
        }

        public Node(int freq, Node left, Node right) {
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;

    /**
     * Constructs a new HuffmanTree from a frequency map.
     *
     * @param freqs a map from 9-bit values to frequencies.
     */
    public HuffmanTree(Map<Short, Integer> freqs) {

    }

    /**
     * Constructs a new HuffmanTree from the given file.
     *
     * @param in the input file (as a BitInputStream)
     */
    public HuffmanTree(BitInputStream in) {
        root = readTree(in);
    }

    private Node readTree(BitInputStream in) {
        int bit = in.readBit();
        if (bit == 0) {
            short value = (short) in.readBits(9);
            int freq = in.readBits(32);
            return new Node(value, freq);
        } else {
            Node left = readTree(in);
            Node right = readTree(in);
            return new Node(left.freq + right.freq, left, right);
        }
    }

    /**
     * Writes this HuffmanTree to the given file as a stream of bits in a
     * serialized format.
     *
     * @param out the output file as a BitOutputStream
     */
    public void serialize(BitOutputStream out) {
        writeTree(root, out);
    }

    private void writeTree(Node node, BitOutputStream out) {
        if (isLeaf(node)) {
            out.writeBit(0);
            out.writeBits(node.value, 9);
        } else {
            out.writeBit(1);
            writeTree(node.left, out);
            writeTree(node.right, out);
        }
    }

    /**
     * Encodes the file given as a stream of bits into a compressed format using
     * this Huffman tree. The encoded values are written, bit-by-bit to the
     * given BitOuputStream.
     *
     * @param in the file to compress.
     * @param out the file to write the compressed output to.
     */
    public void encode(BitInputStream in, BitOutputStream out) {

    }

    /**
     * Decodes a stream of huffman codes from a file given as a stream of bits
     * into their uncompressed form, saving the results to the given output
     * stream. Note that the EOF character is not written to out because it is
     * not a valid 8-bit chunk (it is 9 bits).
     *
     * @param in the file to decompress.
     * @param out the file to write the decompressed output to.
     */
    public void decode(BitInputStream in, BitOutputStream out) {
        Node cur = root;
        while (in.hasBits()) {
            int bit = in.readBit();
            if (bit == 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
            if (isLeaf(cur)) {
                out.writeBits(cur.value, 8);
                cur = root;
            }
        }
    }

    private boolean isLeaf(Node node) {
        return node.left == null && node.right == null;
    }

    private int compare(Node n1, Node n2) {
        return Integer.compare(n1.freq, n2.freq);
    }
}
