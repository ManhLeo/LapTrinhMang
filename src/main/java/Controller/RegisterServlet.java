package Controller;

import java.io.IOException;
import Model.RegisterBO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("Username");
        String password = request.getParameter("Password");

        RegisterBO registerBO = new RegisterBO();
        boolean isRegistered = registerBO.registerUser(username, password);

        if (isRegistered) {
            response.sendRedirect("Login.jsp");
        } else {
            request.setAttribute("errorMessage", "User already exists or invalid input.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);  
        }
    }
}
