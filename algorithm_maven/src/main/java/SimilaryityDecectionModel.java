import com.github.javaparser.Range;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Hao, 27 Feb.
 * <p>
 * This class is constructed for testing only!!
 * There are two code file in Java, each has a PSVM method
 */

public class SimilaryityDecectionModel {
    private ArrayList<SimilarPiece> copyHere = new ArrayList<>();
    private ArrayList<SimilarPiece> changeName = new ArrayList<>();
    private ArrayList<SimilarPiece> similarStructure = new ArrayList<>();
    private ArrayList<CompilationUnit> cuArray = new ArrayList<>();


    /* ************************** constructors ************************ */
    public SimilaryityDecectionModel(ArrayList<String> codeList) throws IOException {
        /* :codeList: codeList[0] is the path of code files, and the rests store the name of code
                      files corresponding to their index in database. */

        // parser all java files and store this.cuArray.
        Path fatherPath = Paths.get(codeList.get(0));
        for (int i = 1; i < codeList.size(); i++) {
            this.cuArray.add(StaticJavaParser.parse(fatherPath.resolve(Paths.get(codeList.get(i)))));
        }

        detectCopy();

    }

    /* ************************** Visitors ************************ */
    private static class MethodNamePrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
            System.out.println("Method Name Printed: " + md.getName());
        }
    }

    private static class MethodNameCollector extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(MethodDeclaration md, List<String> collector) {
            super.visit(md, collector);
            collector.add(md.getNameAsString());
        }
    }


    private static class IntegerLiteralModifier extends ModifierVisitor<Void> {
        @Override
        public FieldDeclaration visit(FieldDeclaration fd, Void arg) {
            super.visit(fd, arg);
            return fd;
        }
    }


    /* ************* detect the copy and change variable name between normal nodes *********** */
    private void detectCopyNode(int f1, int f2, Node node_base, Node node_compare) {
        // break if one of node is Modifier or SimpleName
        if (node_base instanceof Modifier || node_compare instanceof Modifier
                || node_base instanceof SimpleName || node_compare instanceof SimpleName
                || node_base instanceof ClassOrInterfaceType || node_compare instanceof ClassOrInterfaceType
                || node_base instanceof Parameter || node_compare instanceof Parameter
                || node_base instanceof VoidType || node_compare instanceof VoidType)
            return;

        // -------------------------- Compare Between Nodes ------------------------------ //
        if (node_base.equals(node_compare)) {
            copyHere.add(new SimilarPiece(f1, f2, getRange(node_base), getRange(node_compare)));
            return;
        }

        // break if one of node is one-line code
        if (isOneLine(node_base) || isOneLine(node_compare)) return;


        // --------------------- Class, Interface, Method, Constructor -------------------- //
        // the case to compare two CID
        if (node_base instanceof ClassOrInterfaceDeclaration
                && node_compare instanceof ClassOrInterfaceDeclaration) {
            detectCopyClassInterfaceConstructorMethod(f1, f2, node_base, node_compare);
            return;
        }

        // the case to compare two method
        if (node_base instanceof MethodDeclaration && node_compare instanceof MethodDeclaration) {
            detectCopyClassInterfaceConstructorMethod(f1, f2, node_base, node_compare);
            return;
        }

        // the case to compare two constructor
        if (node_base instanceof ConstructorDeclaration && node_compare instanceof ConstructorDeclaration) {
            detectCopyClassInterfaceConstructorMethod(f1, f2, node_base, node_compare);
            return;
        }

        // break if one of node is CID
        if (node_base instanceof ClassOrInterfaceDeclaration
                || node_compare instanceof ClassOrInterfaceDeclaration) return;

        // break if one of node is Method
        if (node_base instanceof MethodDeclaration || node_compare instanceof MethodDeclaration)
            return;

        // break if one of node is Constructor
        if (node_base instanceof ConstructorDeclaration || node_compare instanceof ConstructorDeclaration)
            return;
        // ------------------------------------------------------------------------------------- //


        // case of package, import, comment, whole class and any one-line code
        for (Node base : node_base.getChildNodes()) {
            for (Node node : node_compare.getChildNodes()) {
                detectCopyNode(f1, f2, base, node);
            }
        }
    }


    /* ************* detect the copy between spacial nodes *********** */
    private void detectCopyClassInterfaceConstructorMethod(int f1, int f2, Node node_base, Node node_compare) {
        for (Node ni : node_base.getChildNodes()) {
            for (Node nj : node_compare.getChildNodes()) {
                if (node_base instanceof MethodDeclaration && ni instanceof BlockStmt && nj instanceof BlockStmt) {
                    detectCopyNode(f1, f2, ni, nj);
                    return;
                }
                detectCopyNode(f1, f2, ni, nj);
            }
        }
    }

    /* ************* setup running for copy and change variable name *********** */
    private void detectCopy() {
        CompilationUnit tempi, tempj;
        for (int i = 0; i < (this.cuArray.size() - 1); i++) {
            for (int j = i + 1; j < this.cuArray.size(); j++) {
                tempi = this.cuArray.get(i);
                tempj = this.cuArray.get(j);
                detectCopyNode(i, j, tempi, tempj);

            }
        }
    }

    /* ************************** Utility Functions ************************ */
    /* get the 2-elements index array of begin line and end line of a node */
    private int[] getRange(Node node) {
        Range r = node.getRange().get();
        return new int[]{r.begin.line, r.end.line};
    }

    /* check if node is one-line code */
    private boolean isOneLine(Node node) {
        int[] r = getRange(node);
        return r[1] - r[0] == 0;
    }


    // run python service
    private static void runPythonModel(String command) {

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


    /* ************************** Testing Main ************************ */
    public static void main(String[] args) throws IOException {
        SimilaryityDecectionModel sdm = new SimilaryityDecectionModel(SampleInput.input);

        // print the copy result
        for (SimilarPiece s: sdm.copyHere) System.out.println(s);
    }
}


