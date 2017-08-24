package com.ervin.myblog.repositories.hibernate;

import com.ervin.myblog.entity.User;
import com.ervin.myblog.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
public class UserDAO implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public User getUser(int id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            Query<User> query = currentSession.createQuery("from User where id = :id", User.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public User getUser(String username) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            Query<User> query = currentSession.createQuery("from User where username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }
}
