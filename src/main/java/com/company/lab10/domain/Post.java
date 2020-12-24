package com.company.lab10.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date postingDate;
    @Column
    private String text;

    public Post() {
    }

    public Post(Date postingDate, String text) {
        this.postingDate = postingDate;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
