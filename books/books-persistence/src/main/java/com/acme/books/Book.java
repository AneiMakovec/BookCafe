package com.acme.books;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(
                name = "Book.findBooks",
                query = "SELECT b " +
                        "FROM Book b"
        )
})
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "num_pages")
    private int numPages;

    @Column(name = "publisher")
    private String publisher;

    @Temporal(TemporalType.DATE)
    @Column(name = "publish_date")
    private Date publishDate;


    public int getId() { return book_id; }

    public void setId(int id) { this.book_id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getNumPages() { return numPages; }

    public void setNumPages(int numPages) { this.numPages = numPages; }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Date getPublishDate() { return publishDate; }

    public void setPublishDate(Date publishDate) { this.publishDate = publishDate; }
}
