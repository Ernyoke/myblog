package com.ervin.myblog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ervin.myblog.HomeController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/ervin/myblog/test-configuration.xml" })
public class HomeControllerTests {

	@InjectMocks
	private HomeController homeController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
	}

	@Test
	public void getPostsTest() throws Exception {
		assertNotEquals(homeController, null);
		mockMvc.perform(get("/posts")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$[0].author", is("ervin"))).andExpect(jsonPath("$[0].title", is("title0")))
				.andExpect(jsonPath("$[0].content", is("blogpost0"))).andExpect(jsonPath("$[0].date", is("")));
	}
	
	@After
	public void tearDown() {
		//do nothing here
	}
}
