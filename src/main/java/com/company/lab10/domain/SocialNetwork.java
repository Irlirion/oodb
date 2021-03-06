package com.company.lab10.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SocialNetwork {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SocialNetwork")
    @SequenceGenerator(name = "SocialNetwork", sequenceName = "socialnetwork_seq", allocationSize = 50)
    private Long id;
    @Column
    private String name;
    @OneToMany(mappedBy = "socialNetwork")
    private Set<Community> communities = new HashSet<>();

    public SocialNetwork() {
    }

    public SocialNetwork(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addCommunity(Community community) {
        communities.add(community);
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SocialNetwork{" +
                "name='" + name + '\'' +
                ", communities=" + communities +
                '}';
    }
}
