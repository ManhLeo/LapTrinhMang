package Model;

public class RegisterBO {
	RegisterDAO registerDAO = new RegisterDAO();
    public boolean registerUser(String username, String password) {
        
        return registerDAO.Register(username, password);
    }
}
