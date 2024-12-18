package Controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Model.information;
import Model.PdfToDocxConverter;
import Model.SaveInformationBO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/PdfController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB - RAM threshold
    maxFileSize = 1024 * 1024 * 10,      // 10MB - Max file size
    maxRequestSize = 1024 * 1024 * 50    // 50MB - Total request size
)
public class PdfController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static BlockingQueue<File> pdfQueue = new LinkedBlockingQueue<>();
    private static boolean isProcessing = false;

    public PdfController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String outputPath = "D:\\JAVA_2024\\Data";

        File outputDir = new File(outputPath);

     if (!outputDir.exists()) {
         outputDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
     }

        outputPath = outputPath.replace("\\", "/"); // Chuẩn hóa đường dẫn

        // Lấy danh sách các file đã chọn
        Part[] pdfParts = request.getParts().stream()
                .filter(part -> part.getName().equals("pdfFile"))
                .toArray(Part[]::new);
        
        if (pdfParts.length == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No files uploaded.");
            return;
        }

        // Lấy userId từ session
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("UserID") : null;

        if (userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in.");
            return;
        }

        // Lưu các file vào hàng đợi để xử lý
        for (Part pdfPart : pdfParts) {
            String pdfFileName = pdfPart.getSubmittedFileName();
            if (pdfFileName != null && !pdfFileName.isEmpty()) {
                File uploadedFile = new File(outputPath + "/" + pdfFileName);
                pdfPart.write(uploadedFile.getAbsolutePath());

                // Thêm file vào hàng đợi để xử lý
                pdfQueue.offer(uploadedFile);
            }
        }

        // Nếu chưa bắt đầu xử lý, khởi động xử lý hàng đợi
        if (!isProcessing) {
            isProcessing = true;
            new Thread(new PdfProcessor(outputPath, userId)).start();
        }

        // Không chuyển hướng, vẫn giữ nguyên trang
        //response.getWriter().write("Conversion started, files are being processed.");
    }

    private static class PdfProcessor implements Runnable {
        private String outputPath;
        private Integer userId;

        public PdfProcessor(String outputPath, Integer userId) {
            this.outputPath = outputPath;
            this.userId = userId;
        }
        

        @Override
        public void run() {
            while (!pdfQueue.isEmpty()) {
                File pdfFile = pdfQueue.poll();
                if (pdfFile != null) {
                    processPdf(pdfFile);
                }
            }
            isProcessing = false;
        }

        private void processPdf(File pdfFile) {
            SaveInformationBO saveInformationBO = new SaveInformationBO();
            String status = "Waiting";  // Đặt trạng thái ban đầu là "Waiting"
            String finalOutputDocx = outputPath + "/output_" + pdfFile.getName().replace(".pdf", ".docx");
            Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
            int InforID = saveInformationBO.saveFileInformation(userId, currentTime, pdfFile.getName(), finalOutputDocx, status);
            
            try {
                PdfToDocxConverter.convertPdfToDocx(pdfFile.getAbsolutePath(), finalOutputDocx);
                status = "Success"; // Sau khi chuyển đổi xong, thay đổi trạng thái thành "Success"
            } catch (Exception e) {
                e.printStackTrace();
                status = "Failed"; // Nếu có lỗi trong quá trình chuyển đổi, đánh dấu là "Failed"
            }

            saveInformationBO.UpdateInformation(InforID, status);
        }
    }
}
