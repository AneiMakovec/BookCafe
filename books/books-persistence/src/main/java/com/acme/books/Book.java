package com.acme.books;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(
                name = "Book.findBooks",
                query = "SELECT b FROM Book b"
        ),
        @NamedQuery(
                name = "Book.findByTitle",
                query = "SELECT b FROM Book b WHERE b.title = " + ":title"
        ),
        @NamedQuery(
                name = "Book.findByAuthor",
                query = "SELECT b FROM Book b WHERE b.author = " + ":author"
        ),
        @NamedQuery(
                name = "Book.findByPublisher",
                query = "SELECT b FROM Book b WHERE b.publisher = " + ":publisher"
        )
})
@JsonTypeName("book")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "numPages")
    private int numPages;

    @Column(name = "publisher")
    private String publisher;

    @Temporal(TemporalType.DATE)
    @Column(name = "publishDate")
    private Date publishDate;


    public int getId() { return bookId; }

    public void setId(int id) { this.bookId = id; }

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
