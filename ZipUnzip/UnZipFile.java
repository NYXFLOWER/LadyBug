package huawei001;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnZipFile {
	
	public  static String dir;


	public static void unZipFiles(String zipPath, String descDir) throws IOException {
		unZipFiles(new File(zipPath), descDir);
	}
 	
	public static String unZipFiles(File zipFile, String descDir) throws IOException {
		
		ZipFile zip = new ZipFile(zipFile,Charset.forName("utf-8")); //Solve garbled problems
		String name = zip.getName().substring(zip.getName().lastIndexOf('\\')+1, zip.getName().lastIndexOf('.'));
//		System.out.println("name1 " + name);
		
		
		
		File pathFile = new File(descDir+name);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		
		dir =  descDir + name;
//		System.out.println("dir " + descDir + name);
//		System.out.println("name2 " + descDir + name);
		
		
		
		for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			
			if(zipEntryName.endsWith("java")) {
//				System.out.println("name3 " + zipEntryName);
				
				InputStream in = zip.getInputStream(entry);
				String outPath = (descDir + name +"/"+ zipEntryName).replaceAll("\\*", "/");
//				System.out.println("name out put" + outPath);
				
				// Determine if the path exists. If it does not exist, create a file path.
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				if (!file.exists()) {
					file.mkdirs();
				}
				
				// Determine whether the full path of the file is a folder. If it is uploaded above, you do not need to extract it.
				if (new File(outPath).isDirectory()) {
					continue;
				}
				// print output
				//System.out.println(outPath);
	 
				FileOutputStream out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
			}
		}
		
//		System.out.println("unzip success");
//		System.out.println(dir);
		return dir;	
	}
	
	//test
	public static void main(String[] args) {
		try {
			unZipFiles(new File("C:/Users/think/Desktop/Desktop.zip"), "E:/Study/abc/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 

}
