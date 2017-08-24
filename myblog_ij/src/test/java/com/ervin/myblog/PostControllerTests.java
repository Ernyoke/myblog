package com.ervin.myblog;

import com.ervin.myblog.entity.Post;
import com.ervin.myblog.entity.User;
import com.ervin.myblog.repositories.PostRepository;
import com.ervin.myblog.repositories.UserRepository;
import com.ervin.myblog.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PostControllerTests {

    @InjectMocks
    PostController homeController;

    private MockMvc mockMvc;

    @Mock
    private PostRepository mockPostRepository;

    ArrayList<Post> posts = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        User author = new User("user1");
        Post post1 = new Post(author, "title1", "content1", Utils.getCurrentDate());
        post1.setId(1);
        Post post2 = new Post(author, "title2", "content2", Utils.getCurrentDate());
        post2.setId(2);
        posts.add(post1);
        posts.add(post2);
    }

    @Test
    public void getPostsTest() throws Exception {
        when(mockPostRepository.getPosts()).thenReturn(posts);
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title1")))
                .andExpect(jsonPath("$[0].content", is("content1")))
                .andExpect(jsonPath("$[0].author.username", is("user1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("title2")))
                .andExpect(jsonPath("$[1].content", is("content2")))
                .andExpect(jsonPath("$[1].author.username", is("user1")));
        verify(mockPostRepository, times(1)).getPosts();
        verifyNoMoreInteractions(mockPostRepository);
    }

    @Test
    public void getPostTestValidId() throws Exception {
        int postId = 1;
        int position = 0;
        when(mockPostRepository.getPost(postId)).thenReturn(posts.get(position));
        mockMvc.perform(get("/post").param("id", Integer.toString(postId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.content", is("content1")))
                .andExpect(jsonPath("$.author.username", is("user1")));
        verify(mockPostRepository, times(1)).getPost(postId);
        verifyNoMoreInteractions(mockPostRepository);
    }

    @Test
    public void getPostTestInvalidId() throws Exception {
        int postId = 500;
        when(mockPostRepository.getPost(postId)).thenThrow(new NoResultException());
        mockMvc.perform(get("/post").param("id", Integer.toString(postId)))
                .andExpect(status().isNotFound());
        verify(mockPostRepository, times(1)).getPost(postId);
        verifyNoMoreInteractions(mockPostRepository);
    }

    @Test
    public void inserPostTest() throws Exception {
        User author = new User("author");
        Post post = new Post(author, "title", "content", Utils.getCurrentDate());
        mockMvc.perform(post("/insertpost").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Utils.convertToJsonString(post)))
                .andExpect(status().isCreated());
        verify(mockPostRepository, times(1)).addPost(post);
        verifyNoMoreInteractions(mockPostRepository);
    }

    @Test
    public void updatePostTest() throws Exception {
        User author = new User("author");
        Post post = new Post(author, "title", "content", Utils.getCurrentDate());
        mockMvc.perform(put("/updatepost").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Utils.convertToJsonString(post)))
                .andExpect(status().isAccepted());
        verify(mockPostRepository, times(1)).updatePost(post);
        verifyNoMoreInteractions(mockPostRepository);
    }

    @Test
    public void updatePostInvalidIdTest() throws Exception {
        User author = new User("author");
        Post post = new Post(author, "title", "content", Utils.getCurrentDate());
        doThrow(new IllegalArgumentException("")).when(mockPostRepository).updatePost(post);
        mockMvc.perform(put("/updatepost").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Utils.convertToJsonString(post)))
                .andExpect(status().isNotFound());
        verify(mockPostRepository, times(1)).updatePost(post);
        verifyNoMoreInteractions(mockPostRepository);
    }

    @Test
    public void deletePostTest() throws Exception {
        int id = 1;
        mockMvc.perform(delete("/deletepost").param("id", Integer.toString(id)))
                .andExpect(status().isNoContent());
        verify(mockPostRepository, times(1)).deletePost(id);
        verifyNoMoreInteractions(mockPostRepository);
    }

    @Test
    public void deletePostInvalidIdTest() throws Exception {
        int id = 500;
        doThrow(new NoResultException("")).when(mockPostRepository).deletePost(id);
        mockMvc.perform(delete("/deletepost").param("id", Integer.toString(id)))
                .andExpect(status().isNotFound());
        verify(mockPostRepository, times(1)).deletePost(id);
        verifyNoMoreInteractions(mockPostRepository);
    }
}
