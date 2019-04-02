import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


class LadyBugMain {
    /* setting */
    private static final String destDir = "src/main/resources";
//    private static final String namePartten = "[Assignment]_[StudentID]_[Attempt]_[Date]_{FileName]";
    private static final String pdfPath = "src/main/resources/pdf_dir";

    /* data base */
    private String dir;
    private ArrayList<JavaFileInfo> fileInfos = new ArrayList<>();
    private ArrayList<String> algorithmInput = new ArrayList<>();
    private ArrayList<SimilarPiece> copyDirectly, changeName, similarStructure;


    public LadyBugMain(String zipPath, String outPath) {
        // build algorithm env and construct data base
        build(zipPath);

        // compare among files
        SimilaryityDecectionModel sdm = new SimilaryityDecectionModel(this.algorithmInput);
        this.copyDirectly = sdm.getCopyHere();
        this.changeName = sdm.getChangeName();
        this.similarStructure = sdm.getSimilarStructure();

        // generate result - pdf
        generatePDF();

        // generate zip
        zipFile(outPath);
    }

    private void zipFile(String outPath) {
        InputStream pdf;
        int length;

        try {

            File[] f = new File(LadyBugMain.pdfPath).listFiles();

            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outPath));
            assert f != null;
            for (File file: f){
                // read from pdf
                pdf = new FileInputStream(file);

                // add entry to zip
                ZipEntry e = new ZipEntry(file.getName());
                out.putNextEntry(e);

                // write to zip entry
                byte[] buffer = new byte[1024];
                while ((length = pdf.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

                out.closeEntry();
            }
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private void generatePDF() {
        // write to pdf
        PDF pdf = new PDF(this.fileInfos, this.copyDirectly, this.changeName,
                this.similarStructure);

        // directory for pdf
        File pathDir = new File(LadyBugMain.pdfPath);
        if (!pathDir.exists()) {
            pathDir.mkdirs();
        }

        // directory for java
        File javaDir = new File(this.dir);
        if (!javaDir.exists()) {
            javaDir.mkdirs();
        }

        // generate PDFs
        try {
            for (int i = 1; i < this.algorithmInput.size(); i++)
                pdf.txtToPdf(new File(javaDir, this.algorithmInput.get(i)),
                        new File(pathDir, javaToPDF(this.algorithmInput.get(i))),
                        i - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String javaToPDF(String java) {
        return java.replace(".java", ".pdf");
    }

    private void build(String zipPath) {
        try {
            // unzip and get unzipped files directory
            this.dir = UnZipFile.unZipFiles(new File(zipPath), destDir);

            // read from dir
            File[] files = new File(this.dir).listFiles();
            assert files != null;       // file path is not a dir
            String temp;
            for (File file : files) {
                if (!file.getName().startsWith("__")) {
                    this.dir = String.format("%s/%s", this.dir, file.getName());
                    this.algorithmInput.add(dir);

                    File[] ffs = file.listFiles();
                    assert ffs != null;
                    for (File f : ffs) {
                        temp = f.getName();
                        String[] sp = temp.split("_");
                        this.fileInfos.add(new JavaFileInfo(sp[1], sp[4], temp));
                        this.algorithmInput.add(temp);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        final String inputPath = "/Users/nyxfer/Documents/GitHub/ladybug/test.zip";
        final String outputPath = "src/main/resources/result.zip";
        new LadyBugMain(inputPath, outputPath);
    }
}