import java.util.ArrayList;
import java.util.Random;

/**Created by Hao, 7 May.
 *
 * This class is constructed for testing only!!
 * There are three array lists of piece of similar pairs corresponding to the three similar types.
 * Each list has 6 samples
 *
 * A random seed has been set so that the results can be constant*/

public class SampleOutput {
    private int nsample, nfile, nline;
    public ArrayList<SimilarPiece> copyHere = new ArrayList<>();
    public ArrayList<SimilarPiece> changeName = new ArrayList<>();
    public ArrayList<SimilarPiece> similarStructure = new ArrayList<>();
    Random r;

    public SampleOutput() {
        this.nsample = 6;
        this.nfile = 10;
        this.nline = 18;

        this.r = new Random();
        r.setSeed(1);

        for (int i = 0; i < this.nsample; i++) {
            this.copyHere.add(new SimilarPiece(randFile(), randFile(), randBlock(), randBlock()));
            this.changeName.add(new SimilarPiece(randFile(), randFile(), randBlock(), randBlock()));
            this.similarStructure.add(new SimilarPiece(randFile(), randFile(), randBlock(),
                    randBlock()));
        }
    }

    private int randFile() { return this.r.nextInt(this.nfile); }

    private int[] randBlock() {
        int start = this.r.nextInt(this.nline);
        int size = this.r.nextInt(5);

        return new int[]{start, start+size};
    }

    // Testing
    public static void main(String[] args) {
        SampleOutput s = new SampleOutput();
        for (int i = 0; i < s.nsample; i++) {
            System.out.println(s.changeName.get(i).toString());
            System.out.println(s.copyHere.get(i).toString());
            System.out.println(s.similarStructure.get(i).toString());
        }
    }
}
