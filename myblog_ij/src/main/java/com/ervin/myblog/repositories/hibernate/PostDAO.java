package com.ervin.myblog.repositories.hibernate;

import java.util.List;

import com.ervin.myblog.entity.Post;
import com.ervin.myblog.repositories.PostRepository;
import com.ervin.myblog.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PostDAO implements PostRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Post> query = currentSession.createQuery("from Post", Post.class);
        List<Post> posts = query.getResultList();
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPost(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Post> query = currentSession.createQuery("from Post where id = :id", Post.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void updatePost(Post newPost) {
        Post post = getPost(newPost.getId());
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
    }

    @Override
    @Transactional
    public void deletePost(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Post post = getPost(id);
        currentSession.delete(post);
    }

}
