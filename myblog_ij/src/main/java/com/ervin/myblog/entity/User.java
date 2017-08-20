package com.ervin.myblog.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "MYBLOG_USERS")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="USER_NAME")
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="author")
	private List<Post> posts;
	
	public User() {}
	
	public User(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPost(Post post) {
	    posts.add(post);
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	
}
