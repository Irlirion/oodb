package com.company.lab4.domain;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class Message {
    private String message;
    private User sender;
    private User receiver;
    private Date messageDate;

    public Message() {
    }

    public Message(String message, User sender, User receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.messageDate = new Date();
    }

    @XmlElement(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement(name = "sender")
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @XmlElement(name = "receiver")
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @XmlElement(name = "messageDate")
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
