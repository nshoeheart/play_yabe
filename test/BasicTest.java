import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        new User("ross@gmail.com", "1234", "Ross").save();

        // Retrieve the user with email ross@gmail.com
        User ross = User.find("byEmail", "ross@gmail.com").first();

        // Test
        assertNotNull(ross);
        assertEquals("Ross", ross.fullname);
    }
    @Test
    public void tryConnectAsUser() {
        // Create a new user and save it
        new User("ross@gmail.com", "1234", "Ross").save();

        //Test
        assertNotNull(User.connect("ross@gmail.com", "1234"));
        assertNull(User.connect("ross@gmail.com", "not1234"));
        assertNull(User.connect("notross@gmail.com", "1234"));
    }

}
