package com.acme.beans;

import com.acme.books.Book;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @Transactional
    public void saveBook(Book book) {
        if (book != null)
            em.persist(book);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteBook(int bookId) {
        Book book = em.find(Book.class, bookId);
        if (book != null)
            em.remove(book);
    }
}
