package com.company.lab6.entities;

import com.company.lab6.annotations.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends Person {
    @Id
    private long id;
    @Column
    @Relation(type = RelationType.MANY_TO_MANY, target = User.class)
    private Set<User> friends = new HashSet<>();
    @Column
    @Relation(type = RelationType.ONE_TO_MANY, target = Post.class)
    private Set<Post> posts = new HashSet<>();
    @Column
    @Relation(type = RelationType.ONE_TO_MANY, target = Message.class)
    private Set<Message> messages = new HashSet<>();
    @Column
    @Relation(type = RelationType.MANY_TO_MANY, target = Community.class)
    private Set<Community> communities = new HashSet<>();

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

    public Set<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public long getId() {
        return id;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
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

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
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
