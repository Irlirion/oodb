package com.company.lab2.domain;

import java.util.Date;
import java.util.List;

public class Community {
    private String name;
    private String description;
    private List<Post> posts;
    private List<User> users;
    private List<User> admins;

    public Community(String name, String description, List<Post> posts, List<User> users, List<User> admins) {
        this.name = name;
        this.description = description;
        this.posts = posts;
        this.users = users;
        this.admins = admins;
    }

    public void addPost(String text) {
        Post post = new Post(new Date(), text);
        posts.add(post);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addAdmin(User user) {
        admins.add(user);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
    }

    @Override
    public String toString() {
        return "Communitie{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", posts=" + posts +
                ", users=" + users +
                ", admins=" + admins +
                '}';
    }
}
