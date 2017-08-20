package com.ervin.myblog;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ervin.myblog.dao.UserDAO;
import com.ervin.myblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ervin.myblog.dao.PostDAO;
import com.ervin.myblog.entity.Post;
import org.springframework.web.util.UriTemplate;

@Controller
public class HomeController {

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value="/posts", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Post> getPosts(HttpServletRequest request, Model model) {
        List<Post> posts = postDAO.getPosts();
        return posts;
    }

    @RequestMapping(value="/post", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Post getPost(@RequestParam(value="id", required=false) Integer id) {
        return postDAO.getPost(id);
    }

    @RequestMapping(value="/insertpost", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insertPost(@RequestBody Post newPost, HttpServletRequest request, HttpServletResponse response) {
        postDAO.insertPost(newPost);
        response.setHeader("Location", getLocationForChildResource(request, newPost.getId()));
    }

    private String getLocationForChildResource(HttpServletRequest request, Object childIdentifier) {
        StringBuffer url = request.getRequestURL();
        UriTemplate template = new UriTemplate(url.append("/{childId}").toString());
        return template.expand(childIdentifier).toASCIIString();
    }

}
