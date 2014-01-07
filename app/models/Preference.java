package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Preference extends Model {

    @Required
    public String backColor;

    @Required
    @OneToOne
    public User user;

    public Preference(User user) {
        this.user = user;
        backColor = "default";
    }
}
