package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Admin extends Yabe {

    public static void index() {
        String poster = Security.connected();
        List<Post> posts = Post.find("author.email", poster).fetch();
        render(posts);
    }
}
