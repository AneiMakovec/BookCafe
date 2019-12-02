package com.acme.comments;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

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
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comment_id;

    @Column(name = "content")
    private String content;

    @Temporal(TemporalType.TIME)
    @Column(name = "created_timestamp")
    private Date createdTimestamp;

    @Column(name = "book_id")
    private int bookId;

    @Column(name = "user_id")
    private int userId;



    public int getComment_id() { return comment_id; }

    public void setComment_id(int comment_id) { this.comment_id = comment_id; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Date getCreatedTimestamp() { return createdTimestamp; }

    public void setCreatedTimestamp(Date createdTimestamp) { this.createdTimestamp = createdTimestamp; }

    public int getBookId() { return bookId; }

    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }
}
