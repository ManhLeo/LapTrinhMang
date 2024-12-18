package Model;

public class CheckLoginBO {
    CheckLoginDAO checkLoginDAO = new CheckLoginDAO();

    public int getUserId(String username, String password) {
        return checkLoginDAO.CheckLogin(username, password);
    }
}
