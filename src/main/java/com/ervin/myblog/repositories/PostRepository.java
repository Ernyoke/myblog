package com.ervin.myblog.repositories;

import com.ervin.myblog.entity.Post;

import java.util.List;

public interface PostRepository {

    List<Post> getPosts(int lowerLimit, int upperLimit);
    Post getPost(int id);
    void updatePost(Post newPost) throws IllegalArgumentException;
    void deletePost(int id);
    void addPost(Post post);
}
