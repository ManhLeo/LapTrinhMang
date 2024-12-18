package Controller;

import java.io.IOException;
import java.util.List;

import Model.information;
import Model.SaveInformationBO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/HistoryController")
public class HistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("UserID") : null;

        if (userId == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        // Lấy danh sách lịch sử từ SaveInformationBO
        SaveInformationBO saveInformationBO = new SaveInformationBO();
        List<information> fileHistory = saveInformationBO.getFileHistory(userId);

        // Đặt fileHistory vào request attribute để gửi đến History.jsp
        request.setAttribute("fileHistory", fileHistory);
        // Chuyển hướng tới History.jsp
        request.getRequestDispatcher("History.jsp").forward(request, response);
    }
}