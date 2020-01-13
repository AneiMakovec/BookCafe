package com.acme.beans;

import com.acme.books.Book;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class BooksBean {

    @PersistenceContext(unitName = "books-jpa")
    private EntityManager em;

    public Book get(int bookId) {
        return em.find(Book.class, bookId);
    }

    public List<Book> getBooks() {
        return em.createNamedQuery("Book.findBooks", Book.class).getResultList();
    }

    public List<Book> getBooksByAuthor(String author) {
        return em.createNamedQuery("Book.findByAuthor", Book.class).setParameter("author", author).getResultList();
    }

    public List<Book> getBooksByTitle(String title) {
        return em.createNamedQuery("Book.findByTitle", Book.class).setParameter("title", title).getResultList();
    }

    public List<Book> getBooksByPublisher(String publisher) {
        return em.createNamedQuery("Book.findByPublisher", Book.class).setParameter("publisher", publisher).getResultList();
    }

    @Transactional
    public void saveBook(Book book) {
        if (book != null) {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            em.flush();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteBook(int bookId) {
        Book book = em.find(Book.class, bookId);
        if (book != null)
            em.remove(book);
    }
}
