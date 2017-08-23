package com.ervin.myblog.repositories.hibernate;

import com.ervin.myblog.entity.Post;
import com.ervin.myblog.entity.User;
import com.ervin.myblog.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Repository
public class UserDAO implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public User getUser(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User where id = :id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public User getUser(String username) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User where username = :username", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void addPost(Post post) {
        post.setDate(new Date(System.currentTimeMillis()));
        User author = getUser(1);
        post.setAuthor(author);
        author.addPost(post);
    }


}
