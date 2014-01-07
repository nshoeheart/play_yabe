package controllers;

import play.mvc.Before;
import play.mvc.Controller;
import models.*;

public class Yabe extends Controller {

   protected static User user;

    @Before
   public static void assignUser() {
       if (Security.isConnected()) {
           user = User.find("byEmail", Security.connected()).first();
           renderArgs.put("user", user);
       }

   }
}
