package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class User extends Model {

    @Email
    @Required
    public String email;

    @Required
    public String password;

    @Required
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    public Preference preferences;

    public String fullname;
    public boolean isAdmin;

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        preferences = new Preference(this);
    }

    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }

    public String toString() {
        return email;
    }

//    public void changeBackColor(String backColor) {
//        preferences.changeBackgroundColor(backColor);
//    }
}
