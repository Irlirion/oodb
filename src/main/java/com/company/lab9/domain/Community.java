package com.company.lab9.domain;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Community{
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @OneToMany
    private Set<Post> posts = new HashSet<>();

    public Community() {
    }

    public Community(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addPost(String text) {
        Post post = new Post(new Date(), text);
        posts.add(post);
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



    @Override
    public String toString() {
        return "Communitie{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", posts=" + posts +
                '}';
    }
}
