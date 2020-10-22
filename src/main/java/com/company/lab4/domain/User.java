package com.company.lab4.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User extends Person {
    private List<User> friends = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    public User() {
        super();
    }

    public User(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public void sendMessage(User user, String message) {
        Message mes = new Message(message, this, user);
        messages.add(mes);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
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
                "first name=" + getFirstName() +
                ", last name=" + getLastName() +
                ", friends=" + friends +
                ", posts=" + posts +
                ", messages=" + messages +
                '}';
    }
}
