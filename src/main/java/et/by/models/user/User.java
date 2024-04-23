package et.by.models.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("user")
public class User {
    @Value("${userID}")
    private int userID;
    @Value("${usernames}")
    private String username;
    @Value("${userPassword}")
    private String userPassword;

    public User(){}


    public User(int userID, String username, String userPassword){
        this.userID = userID;
        this.username = username;
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String toString(){
        return userID + ", " + username + ", " + userPassword;
    }
}
