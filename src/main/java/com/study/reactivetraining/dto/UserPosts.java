package com.study.reactivetraining.dto;


import java.util.List;

public class UserPosts {

    private User user;
    private List<Post> posts;

    public UserPosts() {
        super();
    }

    public UserPosts(User user, List<Post> posts) {
        super();
        this.user = user;
        this.posts = posts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
