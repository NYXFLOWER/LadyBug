import java.io.IOException;
import java.util.zip.ZipFile;

class LadyBugMain{
    public LadyBugMain(String zipPath) throws IOException {
        ZipFile zip = new ZipFile(zipPath);

    }

    public static void main(String[] args) throws IOException {
        new LadyBugMain("/Users/nyxfer/Documents/GitHub/ladybug/sample_zip.zip");
    }
}