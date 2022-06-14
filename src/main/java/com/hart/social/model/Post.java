package com.hart.social.model;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    public Long id;
    @Column(name = "created_at")
    public String createdAt;
    @Column(name = "updated_at")
    public String updatedAt;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "likes", nullable = false)
    private Integer likes;

    public Post(){
        // Add here init stuff if needed
    }

    public Post(String createdAt, String updatedAt,
                Long userId, String text, Integer likes) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.text = text;
        this.likes = likes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }


    @Override
    public String toString() {
        return "Post{" +
                ", id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", likes=" + likes +
                '}';
    }
}
