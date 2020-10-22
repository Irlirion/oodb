package com.company.lab4.domain;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class Post {
    private Date postingDate;
    private String text;

    public Post() {
    }

    public Post(Date postingDate, String text) {
        this.postingDate = postingDate;
        this.text = text;
    }

    @XmlElement(name = "postingDate")
    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    @XmlElement(name = "text")
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
