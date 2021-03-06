package com.ervin.myblog.repositories.hibernate;

import com.ervin.myblog.entity.Post;
import com.ervin.myblog.repositories.PostRepository;
import com.ervin.myblog.utils.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;
import java.util.stream.IntStream;

@ContextConfiguration("classpath:/test/hibernate-test-config.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
public class PostDAOTests {

    @Autowired
    private PostRepository postRepository;

    private int postCount = 20;

    @Rule
    public ExpectedException thrown = ExpectedException.none();;

    @Before
    public void setUp() {
    }

    @Test
    public void dummyTest() {
        Assert.assertNotEquals(postRepository, null);
    }

    @Test
    public void getPostsPageTest() {
        int lowerLimit = 5;
        int upperLimit = 15;
        List<Post> posts = postRepository.getPosts(lowerLimit, upperLimit);
        iterateOverPosts(lowerLimit, upperLimit, posts);
    }

    @Test
    public void getPostsPageTestExpectedPostCountBiggerThanExistingPostCount() {
        int lowerLimit = 5;
        int upperLimit = 100;
        List<Post> posts = postRepository.getPosts(lowerLimit, upperLimit);
        iterateOverPosts(lowerLimit, postCount, posts);
    }

    @Test
    public void getPostsPageTestNegativeLowerLimit() {
        int lowerLimit = -5;
        int upperLimit = 15;
        thrown.expect(IllegalArgumentException.class);
        postRepository.getPosts(lowerLimit, upperLimit);
    }

    @Test
    public void getPostsPageTestUpperLesserThanLower() {
        int lowerLimit = 10;
        int upperLimit = 5;
        thrown.expect(IllegalArgumentException.class);
        postRepository.getPosts(lowerLimit, upperLimit);
    }

    @Test
    public void getPostTest() {
        int id = 2;
        Post post = postRepository.getPost(id);
        Assert.assertNotEquals(post, null);
        Assert.assertEquals(post.getId(), id);
        Assert.assertEquals(post.getTitle(), "title" + (id - 1));
        Assert.assertEquals(post.getContent(), "content" + (id - 1));
    }

    @Test
    public void getPostInvalidIdTest() {
        int id = 500;
        Post post = postRepository.getPost(id);
        Assert.assertEquals(post, null);
    }

    @Test
    @WithUserDetails(value="testuser")
    public void addPost() {
        String title = "testTitle";
        String content = "testContent";
        Date date = Utils.getCurrentDate();
        Post post = new Post(null, title, content, date);
        postRepository.addPost(post);
        Post postAdded = postRepository.getPost(postCount + 1);
        Assert.assertNotEquals(postAdded, null);
        Assert.assertEquals(postAdded.getTitle(), title);
        Assert.assertEquals(postAdded.getContent(), content);
    }

    @Test
    public void addPostNotAuthenticated() {
        Post post = new Post(null, "testtitle", "testcontent", Utils.getCurrentDate());
        thrown.expect(AuthenticationCredentialsNotFoundException.class);
        postRepository.addPost(post);
    }

    @Test
    public void updatePost() {
        int id = 1;
        String title = "testTitle";
        String content = "testContent";
        Date date = Utils.getCurrentDate();
        Post post = new Post(null, title, content, date);
        post.setId(id);
        postRepository.updatePost(post);
        Post postAdded = postRepository.getPost(id);
        Assert.assertNotEquals(postAdded, null);
        Assert.assertEquals(postAdded.getTitle(), title);
        Assert.assertEquals(postAdded.getContent(), content);
    }

    @Test
    public void updatePostInvalidId() {
        int id = 500;
        String title = "testTitle";
        String content = "testContent";
        Date date = Utils.getCurrentDate();
        Post post = new Post(null, title, content, date);
        post.setId(id);
        thrown.expect(IllegalArgumentException.class);
        postRepository.updatePost(post);
        postRepository.getPost(id);
    }

    @Test
    public void deletePost() {
        int id = 1;
        postRepository.deletePost(id);
        Post post = postRepository.getPost(id);
        Assert.assertEquals(post, null);
    }

    @Test
    public void deletePostInvalidId() {
        int id = 500;
        thrown.expect(IllegalArgumentException.class);
        postRepository.deletePost(id);
    }

    private void iterateOverPosts(int lowerLimit, int upperLimit, List<Post> posts) {
        int expectedPostCount = upperLimit - lowerLimit;

        Assert.assertNotEquals(posts, null);
        Assert.assertEquals(expectedPostCount, posts.size());
        IntStream.iterate(0, i -> i + 1)
                .limit(expectedPostCount)
                .forEach(i -> {
                    Post post = posts.get(i);
                    int postFix = lowerLimit + i;
                    Assert.assertEquals(postFix + 1, post.getId());
                    Assert.assertEquals("title" + postFix, post.getTitle());
                    Assert.assertEquals("content" + postFix, post.getContent());
                });
    }

}
