package com.ervin.myblog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ervin.myblog.dao.PostDAO;
import com.ervin.myblog.entity.Post;

@Controller
public class HomeController {
	
	@Autowired
	private PostDAO postDAO;
	
	@RequestMapping(value="/posts", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Post> getPosts(HttpServletRequest request, Model model) {
		List<Post> posts = postDAO.getPosts();
		return posts;
	}
	
	@RequestMapping(value="/posts", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addPost(HttpServletRequest request, Model model) {
		return "auth";
	}
	
}
