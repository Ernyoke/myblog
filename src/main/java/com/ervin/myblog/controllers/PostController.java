package com.ervin.myblog.controllers;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ervin.myblog.entity.JsView;
import com.ervin.myblog.repositories.PostRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ervin.myblog.entity.Post;
import org.springframework.web.util.UriTemplate;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    private final int postsPerPage = 10;


    @JsonView(value = JsView.Public.class)
    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Post> getPage(@RequestParam(value = "page") Integer page) throws IllegalArgumentException {
        if (page <= 0) {
            throw new IllegalArgumentException("Invalid page number!");
        } else {
            int lowerLimit = page * postsPerPage - postsPerPage;
            int upperLimit = page * postsPerPage;
            return postRepository.getPosts(lowerLimit, upperLimit);
        }
    }

    @JsonView(value = JsView.Public.class)
    @RequestMapping(value = "/post", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Post getPost(@RequestParam(value = "id") Integer id) throws IllegalArgumentException {
        Post post = postRepository.getPost(id);
        if (post == null) {
            throw new IllegalArgumentException("No such post with id " + id);
        }
        return post;
    }

    @RequestMapping(value = "/insertpost", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insertPost(@RequestBody Post newPost, HttpServletRequest request, HttpServletResponse response) {
        postRepository.addPost(newPost);
        response.setHeader("Location", getLocationForChildResource(request, newPost.getId()));
    }

    @RequestMapping(value = "/updatepost", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePost(@RequestBody Post post, HttpServletRequest request, HttpServletResponse response) {
        postRepository.updatePost(post);
        response.setHeader("Location", getLocationForChildResource(request, post.getId()));
    }

    @RequestMapping(value = "/deletepost", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@RequestParam(value = "id") Integer id) {
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoResultException.class})
    public void handleNoResult() {
        // just return empty 404
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({DataIntegrityViolationException.class})
    public void handleAlreadyExists() {
        // just return empty 409
    }
}
