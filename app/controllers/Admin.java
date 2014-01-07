package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Admin extends Yabe {

    @Before
    static void setConnectedUser() {
        if (Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }

    public static void index() {
        User user = User.find("byEmail", Security.connected()).first();
        renderArgs.put("user", user);
        System.out.println(user.fullname);
        String poster = Security.connected();
        List<Post> posts = Post.find("author.email", poster).fetch();
        render(posts, user);
    }
}
