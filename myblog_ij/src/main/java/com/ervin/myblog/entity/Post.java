package com.ervin.myblog.entity;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="MYBLOG_POSTS")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@ManyToOne
	@JoinColumn(name="USER_ID", nullable=false)
	private User author;

	@Column(name="TITLE")
	private String title;

	@Column(name="CONTENT")
	private String content;

	@Column(name="CREATION_DATE")
	private Date date;

	public Post() {
	}

	public Post(User author, String title, String content, Date date) {
		this.author = author;
		this.title = title;
		this.content = content;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", author=" + author + ", title=" + title + ", content=" + content + ", date=" + date
				+ "]";
	}

}
