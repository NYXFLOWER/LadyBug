import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

class UnzipF {
    public UnzipF(String zipPath, String destDir) {
        unzip(zipPath, destDir);
    }

    private static void unzip(String zipFilePath, String destDir) {


        try {
            File dir = new File(destDir);
            if (!dir.exists()) {
                boolean success = dir.createNewFile();
                if (success) System.out.println("the unzip dir is created successfully.");
            }

            ZipFile zif = new ZipFile(zipFilePath);
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath));

            // make output dir
            ZipEntry ze = zis.getNextEntry();
            File dirzip = new File(dir, ze.toString());
            if (!dirzip.exists()) {
                System.out.println("The file exists.");
            } else {
                boolean success = dirzip.createNewFile();
                if (success)    System.out.println("the file dir is created successfully.");
            }

            while ((ze = zis.getNextEntry()) != null) {
                if (ze.toString().endsWith("java")) {
                    // file name
                    String fileName = ze.getName();

                    // in & out file
                    InputStreamReader in = new InputStreamReader(zif.getInputStream(ze));

                    File file2 = new File(dir, fileName);
                    if (file2.exists()) {
                        System.out.println("The file exists.");
                    } else {
                        boolean success = file2.createNewFile();
                        if (success)    System.out.println("the file is created successfully.");
                    }
                    OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file2));

                    // write to file
                    int data;
                    while ((data = in.read()) != -1) {
                        out.write(data);
                    }
                    out.flush();
                    out.close();
                }




//                BufferedOutputStream bos;
//                bos = new BufferedOutputStream(new FileOutputStream(newFile));
//                int b = 0;
//                while ((b = zis.read()) != -1) {
//                    bos.write(b);
//                }
//                bos.flush();
//                bos.close();


//                FileOutputStream fos = new FileOutputStream(newFile);
//                int len;
//                while ((len = zis.read(buffer)) > 0) {
//                    fos.write(buffer, 0, len);
//                }
//                fos.close();
                //close this ZipEntry
//                zis.closeEntry();
//                ze = zis.getNextEntry();
            }
            //close last ZipEntry
//            zis.closeEntry();
//            zis.close();
//            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

