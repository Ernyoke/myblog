package com.ervin.myblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String username;

    @Column(name="PASSWORD")
    @JsonIgnore
    private String password;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="author")
    private List<Post> posts;

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + username + "]";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
