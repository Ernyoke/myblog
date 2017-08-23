package com.ervin.myblog.repositories;

import com.ervin.myblog.entity.Post;
import com.ervin.myblog.entity.User;

public interface UserRepository {
    User getUser(int id);
    User getUser(String username);
    void addPost(Post post);
}
