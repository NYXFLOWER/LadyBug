import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;


import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;


/**
 * Created by Hao, 27 Feb.
 * <p>
 * This class is constructed for testing only!!
 * There are two code file in Java, each has a PSVM method
 */

public class SimilaryityDecectionModel {
    public ArrayList<SimilarPiece> copyHere = new ArrayList<>();
    public ArrayList<SimilarPiece> changeName = new ArrayList<>();
    public ArrayList<SimilarPiece> similarStructure = new ArrayList<>();

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

    public SimilaryityDecectionModel(ArrayList<String> codeList) throws FileNotFoundException {
        /* codeList[0] is the path of code files, and the rests store the name of code files
        corresponding to their index in database. */
        StringBuilder sb = new StringBuilder();
        String path = codeList.get(0);
        sb.append(path).append(codeList.get(2));


        // -------------------------------------------------------------- //
        CompilationUnit cu = StaticJavaParser.parse(new File(sb.toString()));
//        Statement sss = StaticJavaParser.parseStatement("fff");

        VoidVisitor<?> methodNameVisitor = new MethodNamePrinter();
        methodNameVisitor.visit(cu, null);

        List<String> methodNames = new ArrayList<>();
        VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
        methodNameCollector.visit(cu, methodNames);
        methodNames.forEach(n -> System.out.println("Method Name Collected: " + n));

        System.out.println();
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

//        SampleOutput output = new SampleOutput();
//        System.out.println(output.similarStructure);
//        System.out.println(output.changeName);
//        System.out.println(output.copyHere);
//
//        SimilarPiece p = output.similarStructure.get(0);
//        System.out.println(p.lines1[1]);

    }
}


