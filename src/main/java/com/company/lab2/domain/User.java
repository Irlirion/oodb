package com.company.lab2.domain;

import java.util.Date;
import java.util.List;

public class User extends Person {
    private List<User> friends;
    private List<Post> posts;
    private List<Message> messages;

    public User(String firstName, String lastName, String email, String phone, List<User> friends, List<Post> posts, List<Message> messages) {
        super(firstName, lastName, email, phone);
        this.friends = friends;
        this.posts = posts;
        this.messages = messages;
    }

    public void sendMessage(User user, String message) {
        Message mes = new Message(message, this, user);
        messages.add(mes);
    }

    public void addPost(String text) {
        Post post = new Post(new Date(), text);
        posts.add(post);
    }

    public void removePost(Post post) {
        posts.remove(post);
    }

    public void addFriend(User user) {
        friends.add(user);
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
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
