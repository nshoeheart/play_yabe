package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;
import play.libs.*;
import play.cache.*;

import java.util.*;

import models.*;

public class Application extends Yabe {

    @Before
    static void addDefaults() {
        renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
        renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
    }


    public static void index() {
        Post frontPost = Post.find("order by postedAt desc").first();
        List<Post> olderPosts = Post.find("order by postedAt desc").from(1).fetch(10);
        render(frontPost, olderPosts);
    }

    public static void show(Long id) {
        Post post = Post.findById(id);
        String randomID = Codec.UUID();
        render(post, randomID);
    }

    public static void postComment(
            Long postID,
            @Required(message="Your name is required") String author,
            @Required(message="A message is required") String content,
            @Required(message="Please type the code") String code,
            String randomID) {
        Post post = Post.findById(postID);
        validation.equals(code, Cache.get(randomID)).message("Invalid code. Please type it again");
        if (validation.hasErrors()) {
            params.flash();
            Validation.keep();
            render("Application/show.html", post, randomID);
        }
        post.addComment(author, content);
        flash.success("Thanks for posting, %s", author);
        Cache.delete(randomID);
        show(postID);
    }

    public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#E4EAFD");
        Cache.set(id, code, "10mn");
        renderBinary(captcha);
    }

    public static void listTagged(String tag) {
        List<Post> posts = Post.findTaggedWith(tag);
        render(tag, posts);
    }

    public static void preferences() {
        render();
    }

    public static void changeBackgroundColor(@Required String backColor) {
        if (validation.hasErrors()) {
            render("Application/preferences.html");
        }
        user.preferences.backColor = backColor;
        user.save();
        render("Application/preferences.html", user);
    }
}