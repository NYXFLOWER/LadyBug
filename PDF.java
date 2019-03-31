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
import com.itextpdf.layout.property.VerticalAlignment;


import java.io.*;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PDF {


    public static final String FONT = "C:\\Windows\\Fonts\\simsun.ttc,1";
    ArrayList<JavaFileInfo> fileInfos ;
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


    public static void txtToPdf(String txtPath, String pdfPath, int fileIndex,
                                ArrayList<JavaFileInfo> fileInfos,
                                ArrayList<SimilarPiece> copyDirectly,
                                ArrayList<SimilarPiece> changeName,
                                ArrayList<SimilarPiece> similarStructure)
            throws IOException {
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdf = new PdfDocument(writer);
        PageSize pageSize = PageSize.A4;


        PdfFont font = null;

        try {
            font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            sName = new Paragraph("Student ID : " + fileInfos.get(fileIndex).getStudentID());
            sName.setFont(font1).setFontSize(12);
            sName.setHorizontalAlignment(HorizontalAlignment.LEFT);
            document.add(sName);

            Paragraph codeLength = null;
            codeLength = new Paragraph("File Name : " + fileInfos.get(fileIndex).getFileName());
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


            while ((line = br.readLine()) != null) {

                i++;

                Text text = new Text("%i ." + line.trim());
                Paragraph p = new Paragraph("\n").setFont(font1).setFontSize(8);

                for (int k = 0; k < copyDirectly.size(); k++) {
                    if (i > copyDirectly.get(fileIndex).lines1[0] & i < copyDirectly.get(fileIndex).lines1[1]) {
                        p.setFontColor(Color.RED);
                    }
                }

                for (int m = 0; m < changeName.size(); m++) {
                    if (i > changeName.get(fileIndex).lines1[0] & i < changeName.get(fileIndex).lines1[1]) {
                        p.setFontColor(Color.YELLOW);
                    }
                }

                for (int n = 0; n < similarStructure.size(); n++) {
                    if (i > similarStructure.get(fileIndex).lines1[0] & i < similarStructure.get(fileIndex).lines1[1]) {
                        p.setFontColor(Color.ORANGE);
                    }
                }

                p.add(text);
                document.add(p);

            }

            for (int k = 0; k < copyDirectly.size(); k++) {
                if(! (fileIndex != copyDirectly.get(k).file1)){
                    Text copyText = new Text("Line " + copyDirectly.get(k).lines1[0] + "to line " + copyDirectly.get(k).lines1[1] + "in file which is "
                            +fileInfos.get(k).getFileName() + " is copied directly by line " + copyDirectly.get(k).lines2[0] + " " + "to line " + copyDirectly.get(k).lines2[1]
                            + " in file "+ fileInfos.get(copyDirectly.get(k).file2).getFileName() );
                    Paragraph copyP = new Paragraph("\n").setFont(font1).setFontSize(8).setFontColor(Color.RED);

                    copyP.add(copyText);
                    document.add(copyP);
                }
            }

            for (int m = 0; m < changeName.size(); m++) {
                if(! (fileIndex != changeName.get(m).file1)){
                    Text cNText = new Text("Line " + changeName.get(m).lines1[0] + "to line " + changeName.get(m).lines1[1] + "in file which is "
                            +fileInfos.get(m).getFileName() + " is changed name by line " + changeName.get(m).lines2[0] + " to line " + changeName.get(m).lines2[1]
                            + " in file "+ fileInfos.get(changeName.get(m).file2).getFileName());
                    Paragraph cNP = new Paragraph("\n").setFont(font1).setFontSize(8).setFontColor(Color.YELLOW);

                    cNP.add(cNText);
                    document.add(cNP);
                }
            }

            for (int n = 0; n < similarStructure.size(); n++) {
                if(! (fileIndex != similarStructure.get(n).file1)){
                    Text similarS = new Text("Line " + similarStructure.get(n).lines1[0] + "to line " + similarStructure.get(n).lines1[1] + "in file which is "
                            +fileInfos.get(n).getFileName() + " has similar structure where located line " + similarStructure.get(n).lines2[0] + " to " + "line " + similarStructure.get(n).lines2[1]
                            + " in file "+ fileInfos.get(similarStructure.get(n).file2).getFileName());
                    Paragraph similarSP = new Paragraph("\n").setFont(font1).setFontSize(8).setFontColor(Color.ORANGE);

                    similarSP.add(similarS);
                    document.add(similarSP);
                }
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

    public static void generatePDF(ArrayList<JavaFileInfo> fileInfos,
                                   ArrayList<SimilarPiece> copyDirectly,
                                   ArrayList<SimilarPiece> changeName,
                                   ArrayList<SimilarPiece> similarStructure,
                                   String txtPath, String pdfPath) throws IOException {

        for (int j = 0; j < fileInfos.size(); j++) {

            txtToPdf(txtPath, pdfPath, j, fileInfos, copyDirectly, changeName, similarStructure);
        }
    }


}
