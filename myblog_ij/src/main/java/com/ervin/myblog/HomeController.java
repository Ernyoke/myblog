package com.ervin.myblog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ervin.myblog.repositories.PostRepository;
import com.ervin.myblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ervin.myblog.entity.Post;
import org.springframework.web.util.UriTemplate;

@Controller
public class HomeController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/posts", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Post> getPosts() {
        List<Post> posts = postRepository.getPosts();
        return posts;
    }

    @RequestMapping(value="/post", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Post getPost(@RequestParam(value="id") Integer id) throws IllegalArgumentException {
        Post post = postRepository.getPost(id);
        if (post == null) {
            throw new IllegalArgumentException("No such post with id " + id);
        }
        return post;
    }

    @RequestMapping(value="/insertpost", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insertPost(@RequestBody Post newPost, HttpServletRequest request, HttpServletResponse response) {
        userRepository.addPost(newPost);
        response.setHeader("Location", getLocationForChildResource(request, newPost.getId()));
    }

    @RequestMapping(value="/updatepost", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePost(@RequestBody Post post, HttpServletRequest request, HttpServletResponse response) {
        postRepository.updatePost(post);
        System.out.println(getLocationForChildResource(request, post.getId()));
        response.setHeader("Location", getLocationForChildResource(request, post.getId()));
    }

    @RequestMapping(value="/deletepost", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@RequestParam(value="id") Integer id) {
        postRepository.deletePost(id);
    }

    private String getLocationForChildResource(HttpServletRequest request, Object childIdentifier) {
        StringBuffer url = request.getRequestURL();
        UriTemplate template = new UriTemplate(url.append("/{childId}").toString());
        return template.expand(childIdentifier).toASCIIString();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({IllegalArgumentException.class})
    public void handleNotFound() {
        // just return empty 404
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({DataIntegrityViolationException.class})
    public void handleAlreadyExists() {
        // just return empty 409
    }
}
