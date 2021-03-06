package com.ervin.myblog.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "MYBLOG_USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    @JsonView(value = JsView.Public.class)
    private int id;

    @Column(name="USER_NAME")
    @JsonView(value = JsView.Public.class)
    private String username;

    @Column(name="PASSWORD")
    @JsonView(value = JsView.Internal.class)
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
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((posts == null) ? 0 : posts.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (!User.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        User other = (User)obj;

        if (this.username == null || other.username == null) {
            return false;
        }

        if (other.getUsername().equals(username)) {
            return true;
        }
        return false;
    }


}
