package com.ervin.myblog.repositories.hibernate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ervin.myblog.entity.Post;
import com.ervin.myblog.entity.User;
import com.ervin.myblog.repositories.PostRepository;
import com.ervin.myblog.repositories.UserRepository;
import com.ervin.myblog.security.AccountUserDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
public class PostDAO implements PostRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Post> getPosts(int lowerLimit, int upperLimit) {
        if (lowerLimit < 0) {
            throw new IllegalArgumentException("Illegal lower limit value!");
        }
        if (upperLimit < lowerLimit) {
            throw new IllegalArgumentException("Upper limit is lesser than lower limit!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Post> query = currentSession.createQuery("from Post", Post.class);
        List<Post> posts = query.getResultList();
        if (posts != null) {
            List<Post> postBetweenLimit = new ArrayList<>();
            for (int i = lowerLimit; i < upperLimit && i < posts.size(); ++i) {
                postBetweenLimit.add(posts.get(i));
            }
            return postBetweenLimit;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPost(int id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            Query<Post> query = currentSession.createQuery("from Post where id = :id", Post.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updatePost(Post newPost) {
        Post post = getPost(newPost.getId());
        if (post == null) {
            throw new IllegalArgumentException("No post found with id: " + newPost.getId());
        }
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
    }

    @Override
    @Transactional
    public void deletePost(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Post post = getPost(id);
        if (post == null) {
            throw new IllegalArgumentException("No post found with id: " + id);
        }
        currentSession.delete(post);
    }

    @Override
    @Transactional
    public void addPost(Post post) {
        post.setDate(new Date(System.currentTimeMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            AccountUserDetails userDetails = (AccountUserDetails)authentication.getPrincipal();
            User author = userRepository.getUser(userDetails.getUsername());
            post.setAuthor(author);
            author.addPost(post);
        }
        else {
            throw new AuthenticationCredentialsNotFoundException("No user is authenticated!");
        }
    }

}
