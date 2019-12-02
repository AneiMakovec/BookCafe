package com.acme.beans;

import com.acme.comments.Comment;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
