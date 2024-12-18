package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import Model.CheckLoginBO;

@WebServlet("/CheckLoginServlet")
public class CheckLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CheckLoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("Username");
        String password = request.getParameter("Password");

        CheckLoginBO checkLoginBO = new CheckLoginBO();
        String destination;

        try {
            int userId = checkLoginBO.getUserId(username, password);

            if (userId != -1) {
                // Lưu ID người dùng vào session
                HttpSession session = request.getSession();
                session.setAttribute("UserID", userId);
                session.setAttribute("Username", username);

                destination = "/Main.jsp"; // Chuyển đến trang chính
            } else {
                request.setAttribute("errorMessage", "Invalid username or password.");
                destination = "/Login.jsp"; // Quay lại trang đăng nhập
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred. Please try again later.");
            e.printStackTrace();
            destination = "/Login.jsp";
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
        rd.forward(request, response);
    }
}
