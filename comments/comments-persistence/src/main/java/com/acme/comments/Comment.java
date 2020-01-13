package com.acme.comments;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Entity
@Table(name = "comments")
@NamedQueries({
        @NamedQuery(
                name = "Comment.findComments",
                query = "SELECT c FROM Comment c"
        ),
        @NamedQuery(
                name = "Comment.findByUser",
                query = "SELECT c FROM Comment c WHERE c.userId = " + ":userId"
        ),
        @NamedQuery(
                name = "Comment.findByBook",
                query = "SELECT c FROM Comment c WHERE c.bookId = " + ":bookId"
        )
})
@JsonTypeName("comment")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @Column(name = "content")
    private String content;

    @Temporal(TemporalType.TIME)
    @Column(name = "createdTimestamp")
    private Date createdTimestamp;

    @Column(name = "bookId")
    private int bookId;

    @Column(name = "userId")
    private int userId;



    public int getCommentId() { return commentId; }

    public void setCommentId(int commentId) { this.commentId = commentId; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Date getCreatedTimestamp() { return createdTimestamp; }

    public void setCreatedTimestamp(Date createdTimestamp) { this.createdTimestamp = createdTimestamp; }

    public int getBookId() { return bookId; }

    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }
}
