package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckLoginDAO {
    public int CheckLogin(String username, String password) {
        String query = "SELECT UserID FROM users WHERE Username = ? AND Password = ?";
        try (Connection conn = ConnectDB.connectDatabase();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("UserID"); // Trả về ID người dùng
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }
}
