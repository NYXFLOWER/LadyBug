package huawei001;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class exam003 {

	public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\think\\Desktop\\student 1 - 1324857.zip";
        ZipFile zf = new ZipFile(path);
        InputStream in = new BufferedInputStream(new FileInputStream(path));
        Charset utf = Charset.forName("utf-8");
        ZipInputStream zin = new ZipInputStream(in,utf);
        ZipEntry ze;
        while((ze = zin.getNextEntry()) != null){
            if(ze.toString().endsWith("java")){
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(zf.getInputStream(ze)));
                String line;
                while((line = br.readLine()) != null){
                    System.out.println(line.toString());
                }
                br.close();
            }
            System.out.println();
        }
        zin.closeEntry();
    }

}
