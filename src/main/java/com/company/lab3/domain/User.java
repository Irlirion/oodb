package com.company.lab3.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Date;
import java.util.List;

public class User extends Person {
    private List<User> friends;
    private List<Post> posts;
    private List<Message> messages;

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

    @XmlElementWrapper(name = "messages")
    @XmlElement(name = "message")
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

    @XmlElementWrapper(name = "friends")
    @XmlElement(name = "friend")
    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @XmlElementWrapper(name = "posts")
    @XmlElement(name = "post")
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
