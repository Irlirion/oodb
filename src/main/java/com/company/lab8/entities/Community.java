package com.company.lab8.entities;

import com.company.lab6.annotations.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Community implements com.company.lab8.Entity<Long> {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    @Relation(type = RelationType.ONE_TO_MANY, target = Post.class)
    private Set<Post> posts = new HashSet<>();
    @Column
    @Relation(type = RelationType.MANY_TO_MANY, target = User.class)
    private Set<User> users = new HashSet<>();
    @Column
    @Relation(type = RelationType.MANY_TO_MANY, target = User.class)
    private Set<User> admins = new HashSet<>();

    public Community() {
    }

    public Community(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public Set<User> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<User> admins) {
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
