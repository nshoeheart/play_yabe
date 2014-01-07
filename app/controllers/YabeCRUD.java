package controllers;

import models.User;
import play.mvc.Before;

public class YabeCRUD extends CRUD {
    protected static User user;

    @Before
    public static void assignUser() {
        if (Security.isConnected()) {
            user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }
}
