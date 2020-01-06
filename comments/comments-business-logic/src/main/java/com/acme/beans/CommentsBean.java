package com.acme.beans;

import com.acme.comments.Comment;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class CommentsBean {

    @PersistenceContext(unitName = "comments-jpa")
    private EntityManager em;

    public Comment get(int commentId) {
        return em.find(Comment.class, commentId);
    }

    public List<Comment> getComments() {
        return em.createNamedQuery("Comment.findComments", Comment.class).getResultList();
    }

    public List<Comment> getCommentsByUser(int userId) {
        Query q = em.createNamedQuery("Comment.findByUser", Comment.class);
        q.setParameter("userId", userId);
        return q.getResultList();
    }

    public List<Comment> getCommentsOfBook(int bookId) {
        Query q = em.createNamedQuery("Comment.findByBook", Comment.class);
        q.setParameter("bookId", bookId);
        return q.getResultList();
    }

    @Transactional
    public void saveComment(Comment comment) {
        if (comment != null)
            em.persist(comment);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteComment(int commentId) {
        Comment comment = em.find(Comment.class, commentId);
        if (comment != null)
            em.remove(comment);
    }
}
