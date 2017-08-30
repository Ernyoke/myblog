package com.ervin.myblog.entity;

import com.fasterxml.jackson.annotation.JsonView;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="MYBLOG_POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    @JsonView(value = JsView.Public.class)
    private int id;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable=false)
    @JsonView(value = JsView.Public.class)
    private User author;

    @Column(name="TITLE")
    @JsonView(value = JsView.Public.class)
    private String title;

    @Column(name="CONTENT")
    @JsonView(value = JsView.Public.class)
    private String content;

    @Column(name="CREATION_DATE")
    @JsonView(value = JsView.Public.class)
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        Post other = (Post)obj;

        if (other.getAuthor().equals(author) &&
                other.getTitle().equals(title) &&
                other.getContent().equals(content)) {
            return true;
        }
        return false;
    }
}
