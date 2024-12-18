package Controller;

import Model.SaveInformationBO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/DownloadController")
public class DownloadController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy inforId từ URL
        String inforIdParam = request.getParameter("inforId");

        if (inforIdParam == null || inforIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "inforId is missing.");
            return;
        }

        try {
            int inforId = Integer.parseInt(inforIdParam);

            // Gọi BO để lấy đường dẫn file
            SaveInformationBO saveInformationBO = new SaveInformationBO();
            String filePath = saveInformationBO.getFilePath(inforId);

            if (filePath != null) {
                // Tạo đối tượng File từ đường dẫn
                File downloadFile = new File(filePath);

                if (downloadFile.exists()) {
                    // Cấu hình HTTP response để tải file
                    response.setContentType("application/octet-stream");
                    response.setContentLength((int) downloadFile.length());
                    response.setHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());

                    // Đọc file và ghi vào output stream
                    try (FileInputStream inStream = new FileInputStream(downloadFile);
                         OutputStream outStream = response.getOutputStream()) {

                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = inStream.read(buffer)) != -1) {
                            outStream.write(buffer, 0, bytesRead);
                        }
                    }
                } else {
                    // Nếu file không tồn tại
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                // Nếu không tìm thấy đường dẫn file
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid inforId.");
        }
    }
}