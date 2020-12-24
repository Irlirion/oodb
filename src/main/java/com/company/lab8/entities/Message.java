package com.company.lab8.entities;

import com.company.lab6.annotations.*;

import java.util.Date;

@Entity
public class Message implements com.company.lab8.Entity<Long> {
    @Id
    private Long id;
    @Column
    private String message;
    @Column
    @Relation(type = RelationType.MANY_TO_ONE)
    private User user;
    @Column
    @Relation(type = RelationType.MANY_TO_ONE)
    private User receiver;
    @Column
    private Date messageDate;

    public Message() {
    }

    public Message(String message, User sender, User receiver) {
        this.message = message;
        this.user = sender;
        this.receiver = receiver;
        this.messageDate = new Date();
    }

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return user;
    }

    public void setSender(User sender) {
        this.user = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", sender=" + user +
                ", receiver=" + receiver +
                ", messageDate=" + messageDate +
                '}';
    }
}
