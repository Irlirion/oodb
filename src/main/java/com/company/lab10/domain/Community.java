package com.company.lab10.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Community")
    @SequenceGenerator(name = "Community", sequenceName = "community_seq", allocationSize = 50)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @OneToMany
    private Set<Post> posts = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "socialnetwork_id", nullable = false)
    private SocialNetwork socialNetwork;

    public Community() {
    }

    public Community(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
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
