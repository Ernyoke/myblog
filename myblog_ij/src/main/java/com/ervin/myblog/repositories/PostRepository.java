package com.ervin.myblog.repositories;

import com.ervin.myblog.entity.Post;

import java.util.List;

public interface PostRepository {

    List<Post> getPosts();
    Post getPost(int id);
    void updatePost(Post newPost);
    void deletePost(int id);
}
