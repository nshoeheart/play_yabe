import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }

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

    @Test
    public void createPost() {
        // Create a new user and save it
        User ross = new User("ross@gmail.com", "1234", "Ross").save();

        // Create a new post
        new Post(ross, "My First Post", "Hello ITS!").save();

        // Test that the post has been created
        assertEquals(1, Post.count());

        // Retreive all posts created by Ross
        List<Post> rossPosts = Post.find("byAuthor", ross).fetch();

        // Tests
        assertEquals(1, rossPosts.size());
        Post firstPost = rossPosts.get(0);
        assertNotNull(firstPost);
        assertEquals(ross, firstPost.author);
        assertEquals("My First Post", firstPost.title);
        assertEquals("Hello ITS!", firstPost.content);
        assertNotNull(firstPost.postedAt);
    }

    @Test
    public void postComments() {
        // Create a new user and save it
        User ross = new User("ross@gmail.com", "1234", "Ross").save();

        // Create a new post
        Post rossPost = new Post(ross, "My First Post", "Hello ITC!").save();

        // Post a first comment
        rossPost.addComment("Nathan", "Cool story, bro.").save();
        rossPost.addComment("Kevin", "I like math.").save();

        // Count objects
        assertEquals(1, User.count());
        assertEquals(1, Post.count());
        assertEquals(2, Comment.count());

        // Retreive Ross's post
        rossPost = Post.find("byAuthor", ross).first();
        assertNotNull(rossPost);

        // Check comments
        assertEquals(2, rossPost.comments.size());
        assertEquals("Nathan", rossPost.comments.get(0).author);
        assertEquals("Kevin", rossPost.comments.get(1).author);

        // Delete post
        rossPost.delete();

        // Make sure comments were deleted
        assertEquals(1, User.count());
        assertEquals(0, Post.count());
        assertEquals(0, Comment.count());
    }

}
