import java.io.*;
import java.util.ArrayList;


class LadyBugMain{
    /* setting */
    private static final String destDir = "/Users/nyxfer/Documents/GitHub/ladybug/algorithm-maven" +
            "/src/main/resources";
    private static final String namePartten = "[Assignment]_[StudentID]_[Attempt]_[Date]_{FileName]";

    /* data base */
    private String dir;
    private ArrayList<JavaFileInfo> fileInfos = new ArrayList<>();
    private ArrayList<String> algorithmInput = new ArrayList<>();
    private ArrayList<SimilarPiece> copyDirectly, changeName, similarStructure;


    public LadyBugMain(String zipPath) {
        // build algorithm env and construct data base
        build(zipPath);

        // compare among files
        SimilaryityDecectionModel sdm = new SimilaryityDecectionModel(this.algorithmInput);
        this.copyDirectly = sdm.getCopyHere();
        this.changeName = sdm.getChangeName();
        this.similarStructure = sdm.getSimilarStructure();

        // print copy
        for (SimilarPiece p: this.copyDirectly) {
            Utility.print(p.toString());
        }

        System.out.println(dir);
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
                    for (File f: ffs) {
                        temp = f.getName();
                        String[] sp = temp.split("_");
                        this.fileInfos.add(new JavaFileInfo(sp[1], sp[4]));
                        this.algorithmInput.add(temp);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        new LadyBugMain("/Users/nyxfer/Documents/GitHub/ladybug/test.zip");
    }
}