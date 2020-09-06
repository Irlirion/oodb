package com.company.lab1;

import java.util.List;

public class SocialNetwork {
    private String name;
    private List<Person> users;
    private List<Message> messages;
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

    public List<Person> getUsers() {
        return users;
    }

    public void setUsers(List<Person> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "SocialNetwork{" +
                "name='" + name + '\'' +
                ", users=" + users +
                ", messages=" + messages +
                ", communities=" + communities +
                '}';
    }
}
