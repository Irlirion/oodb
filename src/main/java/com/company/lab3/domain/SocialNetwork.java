package com.company.lab3.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "socialNetwork")
public class SocialNetwork {
    private String name;
    private List<User> users = new ArrayList<>();
    private List<Community> communities = new ArrayList<>();

    public SocialNetwork() {
    }

    public SocialNetwork(String name) {
        this.name = name;
    }

    public void addUser(User user) {users.add(user);}

    public void addCommunity(Community community) {communities.add(community);}

    @XmlElementWrapper(name = "communities")
    @XmlElement(name = "communitie")
    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
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
