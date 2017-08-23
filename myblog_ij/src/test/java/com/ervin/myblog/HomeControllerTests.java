package com.ervin.myblog;

import com.ervin.myblog.entity.Post;
import com.ervin.myblog.entity.User;
import com.ervin.myblog.repositories.PostRepository;
import com.ervin.myblog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class HomeControllerTests {

    @InjectMocks
    HomeController homeController;

    private MockMvc mockMvc;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PostRepository mockPostRepository;

    ArrayList<Post> posts = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        User author = new User("user");
        Post post1 = new Post(author, "title1", "content1",
                new Date(Calendar.getInstance().getTimeInMillis()));
        Post post2 = new Post(author, "title2", "content2",
                new Date(Calendar.getInstance().getTimeInMillis()));
        posts.add(post1);
        posts.add(post2);
    }

    @Test
    public void getPostsTest() throws Exception {
        when(mockPostRepository.getPosts()).thenReturn(posts);
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(mockPostRepository, times(1)).getPosts();
        verifyNoMoreInteractions(mockPostRepository);
    }
}
