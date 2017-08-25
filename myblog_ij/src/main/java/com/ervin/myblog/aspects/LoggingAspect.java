package com.ervin.myblog.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    @Before("execution(public java.util.List<com.ervin.myblog.entity.Post> " +
            "com.ervin.myblog.controllers.PostController.getPosts())")
    public void beforeGetPosts() {
        System.out.println("=========> getPosts() called!");
    }

    @After("execution(public java.util.List<com.ervin.myblog.entity.Post> " +
            "com.ervin.myblog.controllers.PostController.getPosts())")
    public void afterGetPosts() {
        System.out.println("=========> getPosts() executed!");
    }

    @Before("execution(public void com.ervin.myblog.controllers.PostController.deletePost(*))")
    public void beforeDeletePost() {
        System.out.println("=========> deletPost(id) called!");
    }

    @After("execution(public void com.ervin.myblog.controllers.PostController.deletePost(*))")
    public void afterDeletePost() {
        System.out.println("=========> Post deleted!");
    }

}
