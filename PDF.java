import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;


import java.io.*;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PDF {


    //    public static final String FONT = "C:\\Windows\\Fonts\\simsun.ttc,1";
    ArrayList<JavaFileInfo> fileInfos;
    ArrayList<SimilarPiece> copyDirectly, changeName, similarStructure;

    public PDF(ArrayList<JavaFileInfo> fileInfos,
               ArrayList<SimilarPiece> copyDirectly,
               ArrayList<SimilarPiece> changeName,
               ArrayList<SimilarPiece> similarStructure) {
        this.changeName = changeName;
        this.copyDirectly = copyDirectly;
        this.similarStructure = similarStructure;
        this.fileInfos = fileInfos;
    }


    public void txtToPdf(File txtPath, File pdfPath, int fileIndex) throws IOException {
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdf = new PdfDocument(writer);
        PageSize pageSize = PageSize.A4;


//        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

        Document document = new Document(pdf, pageSize);

        BufferedReader br = null;

        try {

            PdfFont titleFont = PdfFontFactory.createFont(FontConstants.COURIER_BOLD);
            Paragraph title1 = null;
            title1 = new Paragraph("Code Similarity Report");
            title1.setFont(titleFont).setFontSize(24);
            title1.setTextAlignment(TextAlignment.CENTER);
            document.add(title1);

            PdfFont font1 = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

            Paragraph sName = null;
            sName = new Paragraph("Student ID : " + this.fileInfos.get(fileIndex).getStudentID());
            sName.setFont(font1).setFontSize(12);
            sName.setHorizontalAlignment(HorizontalAlignment.LEFT);
            document.add(sName);

            Paragraph codeLength = null;
            codeLength =
                    new Paragraph("File Name : " + this.fileInfos.get(fileIndex).getFileName());
            codeLength.setFont(font1).setFontSize(12);
            codeLength.setHorizontalAlignment(HorizontalAlignment.LEFT);
            document.add(codeLength);

            Paragraph ts = null;
            ts = new Paragraph("Total Similarity : ");
            ts.setFont(font1).setFontSize(12);
            ts.setHorizontalAlignment(HorizontalAlignment.LEFT);
            document.add(ts);


            br = new BufferedReader(new InputStreamReader(new FileInputStream(txtPath), "GBK"));
            String line = null;
            int i = 0;

            int count = 0;
            for (int k = 0; k < this.copyDirectly.size(); k++) {
                if (fileIndex == this.copyDirectly.get(k).file1) {
                    Text copyText = new Text(String.format("%s. Line %d to line %d in file %s is copied directly by line %d to line %d in file %s",
                            Integer.toString(++count), this.copyDirectly.get(k).lines1[0], this.copyDirectly.get(k).lines1[1], this.fileInfos.get(fileIndex).getFileName(), this.copyDirectly.get(k).lines2[0], this.copyDirectly.get(k).lines2[1], this.fileInfos.get(this.copyDirectly.get(k).file2).getFileName()));
                    Paragraph copyP = new Paragraph().setFont(font1).setFontSize(8).setFontColor(Color.RED);

                    copyP.add(copyText);
                    document.add(copyP);
                } else if (fileIndex == this.copyDirectly.get(k).file2) {
                    Text copyText = new Text(String.format("%s. Line %d to line %d in file %s is copied directly by line %d to line %d in file %s",
                            Integer.toString(++count), this.copyDirectly.get(k).lines2[0], this.copyDirectly.get(k).lines2[1], this.fileInfos.get(fileIndex).getFileName(), this.copyDirectly.get(k).lines1[0], this.copyDirectly.get(k).lines1[1], this.fileInfos.get(this.copyDirectly.get(k).file1).getFileName()));
                    Paragraph copyP = new Paragraph().setFont(font1).setFontSize(8).setFontColor(Color.RED);

                    copyP.add(copyText);
                    document.add(copyP);
                }
            }

            for (int m = 0; m < this.changeName.size(); m++) {
                if (fileIndex == this.changeName.get(m).file1) {
                    Text cNText = new Text(String.format("%s. Line %d to line %d in file %s is changed name by line %d to line %d in file %s", Integer.toString(++count), this.changeName.get(m).lines1[0], this.changeName.get(m).lines1[1], this.fileInfos.get(fileIndex).getFileName(), this.changeName.get(m).lines2[0], this.changeName.get(m).lines2[1], this.fileInfos.get(this.changeName.get(m).file2).getFileName()));
                    Paragraph cNP = new Paragraph().setFont(font1).setFontSize(8).setFontColor(Color.YELLOW);

                    cNP.add(cNText);
                    document.add(cNP);
                } else if (fileIndex == this.changeName.get(m).file2) {
                    Text cNText = new Text(String.format("%s. Line %d to line %d in file %s is changed name by line %d to line %d in file %s",
                            Integer.toString(++count), this.changeName.get(m).lines2[0], this.changeName.get(m).lines2[1], this.fileInfos.get(fileIndex).getFileName(), this.changeName.get(m).lines1[0], this.changeName.get(m).lines1[1], this.fileInfos.get(this.changeName.get(m).file1).getFileName()));
                    Paragraph cNP = new Paragraph().setFont(font1).setFontSize(8).setFontColor(Color.YELLOW);

                    cNP.add(cNText);
                    document.add(cNP);
                }
            }

            for (int n = 0; n < this.similarStructure.size(); n++) {
                if (fileIndex == this.similarStructure.get(n).file1) {
                    Text similarS = new Text(String.format("%s. Line %d to line %d in file %s has similar structure where located line %d to line %d in file %s",
                            Integer.toString(++count), this.similarStructure.get(n).lines1[0], this.similarStructure.get(n).lines1[1], this.fileInfos.get(fileIndex).getFileName(), this.similarStructure.get(n).lines2[0], similarStructure.get(n).lines2[1], this.fileInfos.get(this.similarStructure.get(n).file2).getFileName()));
                    Paragraph similarSP = new Paragraph("\n").setFont(font1).setFontSize(8).setFontColor(Color.ORANGE);

                    similarSP.add(similarS);
                    document.add(similarSP);
                }else if (fileIndex == this.similarStructure.get(n).file2){
                    Text similarS = new Text(String.format("%s. Line %d to line %d in file %s has similar structure where located line %d to line %d in file %s",
                            Integer.toString(++count), this.similarStructure.get(n).lines2[0], this.similarStructure.get(n).lines2[1], this.fileInfos.get(fileIndex).getFileName(), this.similarStructure.get(n).lines1[0], similarStructure.get(n).lines1[1], this.fileInfos.get(this.similarStructure.get(n).file1).getFileName()));
                    Paragraph similarSP = new Paragraph("\n").setFont(font1).setFontSize(8).setFontColor(Color.ORANGE);

                    similarSP.add(similarS);
                    document.add(similarSP);
                }
            }

            Text liner = new Text("-------------------- show code details-----------------------------");
            Paragraph pline = new Paragraph().setFontColor(Color.GRAY).setFontSize(6);
            pline.add(liner);
            document.add(pline);

            while ((line = br.readLine()) != null) {

                i++;

                Text text = new Text(i + ". " + line.trim());
                Paragraph p = new Paragraph().setFont(font1).setFontSize(8).setFontColor(Color.GRAY);

                for (int k = 0; k < this.copyDirectly.size(); k++) {
                    if (fileIndex == this.copyDirectly.get(k).file1 &&
                            i >= this.copyDirectly.get(k).lines1[0] && i <= this.copyDirectly.get(k).lines1[1]) {
                        p.setFontColor(Color.RED);
                    } else if (fileIndex == this.copyDirectly.get(k).file2 &&
                            i >= this.copyDirectly.get(k).lines2[0] && i <= this.copyDirectly.get(k).lines2[1]) {
                        p.setFontColor(Color.RED);
                    }
                }

                for (int m = 0; m < this.changeName.size(); m++) {
                    if (this.fileInfos.get(fileIndex).getFileName() == this.fileInfos.get(this.changeName.get(m).file1).getFileName() &&
                            i >= this.changeName.get(m).lines1[0] && i <= this.changeName.get(m).lines1[1]) {
                        p.setFontColor(Color.YELLOW);
                        p.add(text);
                        document.add(p);
                    } else if (this.fileInfos.get(fileIndex).getFileName() == this.fileInfos.get(this.changeName.get(m).file2).getFileName() &&
                            i >= this.changeName.get(m).lines2[0] && i <= this.changeName.get(m).lines2[1]) {
                        p.setFontColor(Color.YELLOW);
                        p.add(text);
                        document.add(p);
                    }
                }

                for (int n = 0; n < this.similarStructure.size(); n++) {
                    if (this.fileInfos.get(fileIndex).getFileName() == this.fileInfos.get(this.similarStructure.get(n).file1).getFileName() &&
                            i >= this.similarStructure.get(n).lines1[0] && i <= this.similarStructure.get(n).lines1[1]) {
                        p.setFontColor(Color.ORANGE);
                        p.add(text);
                        document.add(p);
                    } else if (this.fileInfos.get(fileIndex).getFileName() == this.fileInfos.get(this.similarStructure.get(n).file2).getFileName() &&
                            i >= this.similarStructure.get(n).lines2[0] && i <= this.similarStructure.get(n).lines2[1]) {
                        p.setFontColor(Color.ORANGE);
                        p.add(text);
                        document.add(p);
                    }
                }

                p.add(text);
                document.add(p);


            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
                if (writer != null) {
                    writer.close();
                }

                if (pdf != null) {
                    pdf.close();
                }
            }
        }
    }

    // method: search input int is in the CopyArrayList or not
    // input: int (line), file index(f1)
    // output: piece of similarity (line (form i to j) in f1 is similar to line (from k to l) in f2 with type p)

}
