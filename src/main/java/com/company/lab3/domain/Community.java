package com.company.lab3.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Community {
    private String name;
    private String description;
    private List<Post> posts = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<User> admins = new ArrayList<>();

    public Community() {
    }

    public Community(String name, String description) {
        this.name = name;
        this.description = description;
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

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElementWrapper(name = "posts")
    @XmlElement(name = "post")
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @XmlElementWrapper(name = "admins")
    @XmlElement(name = "admin")
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
