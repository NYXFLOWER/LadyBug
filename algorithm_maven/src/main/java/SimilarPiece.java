import java.util.Arrays;

/**Created by Hao, 6 March.
 *
 * This class defines the structure of piece of similar code between line/lines in two files
 * A instance of this class means:
 *      the [lines1] of [file1] is similar to the [lines2] of [file2] */

public class SimilarPiece {
    int file1, file2;
    int[] lines1;
    int[] lines2;

    public SimilarPiece(int file1, int file2, int[] lines1, int[] lines2) {
        /* The length of lines1 and lines2 should be 2
               1. The code block start from [0] and end with [1]
               2. If the index in [0] and [1] is the same, it is the one-line similar code */
        this.file1 = file1;
        this.file2 = file2;
        this.lines1 = lines1;
        this.lines2 = lines2;
    }

    @Override
    public String toString() {
        return "SimilarPiece{" +
                "file1=" + file1 +
                ", file2=" + file2 +
                ", lines1=" + Arrays.toString(lines1) +
                ", lines2=" + Arrays.toString(lines2) +
                '}';
    }

    // Test Main
    public static void main(String[] args) {
        int[] l1 = {1, 2, 3};
        int[] l2 = {2, 3, 4};
        SimilarPiece a = new SimilarPiece(1, 3, l1, l2);
        for (int i: a.lines1) {
            System.out.println(i);
        }

    }
}
