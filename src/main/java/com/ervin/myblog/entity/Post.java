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
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (!Post.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        Post other = (Post)obj;

        if (other.author == null || this.author == null) {
            return false;
        }

        if (other.title == null || this.title == null) {
            return false;
        }

        if (other.content == null || this.content == null) {
            return false;
        }

        if (other.author.equals(author) &&
                other.title.equals(title) &&
                other.content.equals(content)) {
            return true;
        }
        return false;
    }
}
