package com.company.lab6.entities;

import com.company.lab6.annotations.*;

import java.util.Date;

@Entity
public class Message {
    @Id
    private long id;
    @Column
    private String message;
    @Column
    @Relation(type = RelationType.MANY_TO_ONE)
    private User sender;
    @Column
    @Relation(type = RelationType.MANY_TO_ONE)
    private User receiver;
    @Column
    private Date messageDate;

    public Message() {
    }

    public Message(String message, User sender, User receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.messageDate = new Date();
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
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
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", messageDate=" + messageDate +
                '}';
    }
}
