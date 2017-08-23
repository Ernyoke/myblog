package com.ervin.myblog.dao;

import java.sql.Date;
import java.util.List;

import com.ervin.myblog.entity.Post;
import com.ervin.myblog.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PostDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDAO userDAO;

    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Post> query = currentSession.createQuery("from Post", Post.class);
        List<Post> posts = query.getResultList();
        return posts;
    }

    @Transactional(readOnly = true)
    public Post getPost(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Post> query = currentSession.createQuery("from Post where id = :id", Post.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    public void insertPost(Post post) {
        post.setDate(new Date(System.currentTimeMillis()));
        User author = userDAO.getUser(1);
        post.setAuthor(author);
        author.addPost(post);
    }

    @Transactional
    public void updatePost(Post newPost) {
        Post post = getPost(newPost.getId());
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
    }

    @Transactional
    public void deletePost(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Post post = getPost(id);
        currentSession.delete(post);
    }

}
