package com.company.lab6.entities;

import com.company.lab6.annotations.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class SocialNetwork {
    @Id
    private long id;
    @Column
    private String name;
    @Column
    @Relation(type = RelationType.ONE_TO_MANY, target = User.class)
    private Set<User> users = new HashSet<>();
    @Column
    @Relation(type = RelationType.ONE_TO_MANY, target = Community.class)
    private Set<Community> communities = new HashSet<>();

    public SocialNetwork() {
    }

    public SocialNetwork(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public long getId() {
        return id;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "SocialNetwork{" +
                "name='" + name + '\'' +
                ", users=" + users +
                ", communities=" + communities +
                '}';
    }
}
