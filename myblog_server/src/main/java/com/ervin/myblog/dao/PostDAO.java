package com.ervin.myblog.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ervin.myblog.entity.Post;

@Repository
public class PostDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<Post> getPosts() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Post> query = currentSession.createQuery("from Post", Post.class);
		List<Post> posts = query.getResultList();
		return posts;
	}

}
