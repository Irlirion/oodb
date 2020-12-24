package com.company.lab8.entities;

import com.company.lab6.annotations.Column;
import com.company.lab6.annotations.Entity;
import com.company.lab6.annotations.Id;

import java.util.Date;

@Entity
public class Post implements com.company.lab8.Entity<Long> {
    @Id
    private Long id;
    @Column
    private Date postingDate;
    @Column
    private String text;
    @Column
    private long community_id;

    public Post() {
    }

    public Post(Date postingDate, String text) {
        this.postingDate = postingDate;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    @Override
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
