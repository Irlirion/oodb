package com.company.lab6.entities;

import com.company.lab6.annotations.Column;
import com.company.lab6.annotations.Entity;
import com.company.lab6.annotations.Id;

import java.util.Date;

@Entity
public class Post {
    @Id
    private long id;
    @Column
    private Date postingDate;
    @Column
    private String text;

    public Post() {
    }

    public Post(Date postingDate, String text) {
        this.postingDate = postingDate;
        this.text = text;
    }

    public long getId() {
        return id;
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
