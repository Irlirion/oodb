package com.company.lab2.domain;

import java.util.Date;

public class Post {
    private Date postingDate;
    private String text;

    public Post(Date postingDate, String text) {
        this.postingDate = postingDate;
        this.text = text;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postingDate=" + postingDate +
                ", text='" + text + '\'' +
                '}';
    }
}
