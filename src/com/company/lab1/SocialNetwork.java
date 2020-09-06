package com.company.lab1;

import java.util.List;

public class SocialNetwork {
    private String name;
    private List<User> users;
    private List<Communitie> communities;

    public SocialNetwork(String name) {
        this.name = name;
    }

    public List<Communitie> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Communitie> communities) {
        this.communities = communities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
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
