import com.github.javaparser.ast.CompilationUnit;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.ArrayList;
import com.github.javaparser.JavaParser;

/**Created by Hao, 27 Feb.
 *
 * This class is constructed for testing only!!
 * There are two code file in Java, each has a PSVM method */

public class SimilaryityDecectionModel {
    public ArrayList<SimilarPiece> copyHere = new ArrayList<>();
    public ArrayList<SimilarPiece> changeName = new ArrayList<>();
    public ArrayList<SimilarPiece> similarStructure = new ArrayList<>();

    public SimilaryityDecectionModel(ArrayList<String> codeList) {
        /* codeList[0] is the path of code files, and the rests store the name of code files
        corresponding to their index in database. */
        StringBuilder sb = new StringBuilder();
        String path = codeList.get(0);

        File sourceFile = new File(String.join(path, codeList.get(1)));


        System.out.println(path);
    }

    private void runPythonModel(String command) {

        Runtime run = Runtime.getRuntime();

        try {
            Process p = run.exec(command);
            InputStream in = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }

            System.out.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // testing
    public static void main(String[] args) throws IOException {
        SimilaryityDecectionModel sdm = new SimilaryityDecectionModel(SampleInput.input);

    }
}
