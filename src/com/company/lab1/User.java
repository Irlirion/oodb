package com.company.lab1;

import java.util.Date;
import java.util.List;

public class User extends Person {
    private List<User> friends;
    private List<Post> posts;

    public User(String firstName, String lastName, String email, String phone, List<User> friends, List<Post> posts) {
        super(firstName, lastName, email, phone);
        this.friends = friends;
        this.posts = posts;
    }

    public void addPost(String text) {
        Post post = new Post(new Date(), text);
        posts.add(post);
    }

    public void addFriend(User user) {
        friends.add(user);
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "friends=" + friends +
                ", posts=" + posts +
                '}';
    }
}
