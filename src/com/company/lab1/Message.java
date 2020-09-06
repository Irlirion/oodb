package com.company.lab1;

import java.util.Date;

public class Message {
    private String message;
    private Person sender;
    private Person receiver;
    private Date messageDate;

    public Message(String message, Person sender, Person receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.messageDate = new Date();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
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
