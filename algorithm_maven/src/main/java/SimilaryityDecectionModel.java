import com.github.javaparser.Range;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
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


    private void detectCopyNode(int f1, int f2, Node node_base, Node node_compare) {
        if (node_base.equals(node_compare)) {
            copyHere.add(new SimilarPiece(f1, f2, getRange(node_base), getRange(node_compare)));
            return;
        }

        // break if one of node is one-line code
        if (isOneLine(node_base) | isOneLine(node_compare)) return;

        // the case to compare two CID

        if (node_base instanceof ClassOrInterfaceDeclaration
                && node_compare instanceof ClassOrInterfaceDeclaration) {
                    for (Node ni: node_base.getChildNodes()) {
                        for (Node nj: node_compare.getChildNodes()) {
                            detectCopyClassInterface(f1, f2, ni, nj);
                        }
                    }
            return;
        }

        // break if one of node is CID
        if (node_base instanceof ClassOrInterfaceDeclaration
                || node_compare instanceof ClassOrInterfaceDeclaration) return;

        // case of package, import, comment, whole class and any one-line code
        for (Node base : node_base.getChildNodes()) {
            for (Node node : node_compare.getChildNodes()) {
                detectCopyNode(f1, f2, base, node);
            }
        }
    }


    private void detectCopyClassInterface(int i, int j, Node ni, Node nj) {

    }

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


    public SimilaryityDecectionModel(ArrayList<String> codeList) throws IOException {
        /* :codeList: codeList[0] is the path of code files, and the rests store the name of code
                      files corresponding to their index in database. */

        // parser all java files and store this.cuArray.
        Path fatherPath = Paths.get(codeList.get(0));
        for (int i = 1; i < codeList.size(); i++) {
            this.cuArray.add(StaticJavaParser.parse(fatherPath.resolve(Paths.get(codeList.get(i)))));
        }

        detectCopy();

        // read file by file

        // compare the 'import' section

        // compare the class variable declaration

        // compare the class constructor

        // compare the


//        VoidVisitor<?> methodNameVisitor = new MethodNamePrinter();
////        methodNameVisitor.visit(cu, null);
//
//
//        List<String> methodNames = new ArrayList<>();
//        VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
////        methodNameCollector.visit(cu, methodNames);
//        methodNames.forEach(n -> System.out.println("Method Name Collected: " + n));


//        System.out.println();
//        List<Node> a = cu.getChildNodes();
//        System.out.println(a.get(0));


        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        // with src/main/resources appended.
//        SourceRoot sourceRoot =
//                new SourceRoot(CodeGenerationUtils.mavenModuleRoot(SampleInput.class).resolve(path));

        // Our sample is in the root of this directory, so no package name.
//        CompilationUnit cu = sourceRoot.parse("", "SampleInput.java");
//        System.out.println();
//
//        cu.accept(new ModifierVisitor<Void>() {
//            /**
//             * For every if-statement, see if it has a comparison using "!=".
//             * Change it to "==" and switch the "then" and "else" statements around.
//             */
//            @Override
//            public Visitable visit(IfStmt n, Void arg) {
//                // Figure out what to get and what to cast simply by looking at the AST in a debugger!
//                n.getCondition().ifBinaryExpr(
//                        binaryExpr -> {
//                            if (binaryExpr.getOperator() == BinaryExpr.Operator.NOT_EQUALS && n.getElseStmt().isPresent()) {
//                            /* It's a good idea to clone nodes that you move around.
//                                JavaParser (or you) might get confused about who their parent is!
//                            */
//                                Statement thenStmt = n.getThenStmt().clone();
//                                Statement elseStmt = n.getElseStmt().get().clone();
//                                n.setThenStmt(elseStmt);
//                                n.setElseStmt(thenStmt);
//                                binaryExpr.setOperator(BinaryExpr.Operator.EQUALS);
//                            }
//                        });
//                return super.visit(n, arg);
//            }
//        }, null);

        // This saves all the files we just read to an output directory.
//        sourceRoot.saveAll(
//                // The path of the Maven module/project which contains the LogicPositivizer class.
//                CodeGenerationUtils.mavenModuleRoot(SimilaryityDecectionModel.class)
//                        // appended with a path to "output"
//                        .resolve(Paths.get(path)));

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
        System.out.println();
        sdm.cuArray.get(0).getChildNodes();


//        SampleOutput output = new SampleOutput();
//        System.out.println(output.similarStructure);
//        System.out.println(output.changeName);
//        System.out.println(output.copyHere);
//

    }
}


