package Model;

import com.spire.pdf.PdfDocument;
import com.spire.doc.Document;
import com.spire.doc.Section;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.FileFormat;
import java.io.IOException;

public class PdfToDocxConverter {

    // Chuyển PDF thành DOCX
    public static void convertPdfToDocx(String pdfFilePath, String docxOutputPath) {
        // Tạo đối tượng PdfDocument từ Spire.PDF
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(pdfFilePath); // Tải PDF vào PdfDocument

        // Chuyển đổi PDF sang DOCX
        pdf.saveToFile(docxOutputPath, com.spire.pdf.FileFormat.DOCX);
        System.out.println("Chuyển đổi PDF sang DOCX thành công: " + docxOutputPath);
    }

    // Nối các file DOCX vào một file duy nhất
    public static void mergeDocxFiles(String[] docxFiles, String outputDocx) throws IOException {
        Document mergedDoc = new Document();

        for (String docxFile : docxFiles) {
            Document doc = new Document();
            doc.loadFromFile(docxFile);

            for (int j = 0; j < doc.getSections().getCount(); j++) {
                Section section = doc.getSections().get(j);
                for (int k = 0; k < section.getParagraphs().getCount(); k++) {
                    Paragraph paragraph = section.getParagraphs().get(k);
                    String paragraphText = paragraph.getText();
                    
                    // Kiểm tra và xóa thông báo
                    if (paragraphText.contains("Evaluation Warning : The document was created with Spire.PDF for java.")) {
                        section.getParagraphs().removeAt(k);
                        k--;
                    }
                }
                mergedDoc.importSection(section);
            }
        }

        // Lưu tài liệu đã nối vào file output
        mergedDoc.saveToFile(outputDocx, FileFormat.Docx);
        System.out.println("Nối các file DOCX thành công vào: " + outputDocx);
    }
}