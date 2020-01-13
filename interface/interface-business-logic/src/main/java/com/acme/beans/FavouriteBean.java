package com.acme.beans;

import com.acme.users.Favourite;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class FavouriteBean {

    @PersistenceContext(unitName = "users-jpa")
    private EntityManager em;

    public Favourite get(int favouriteId) {
        return em.find(Favourite.class, favouriteId);
    }

    public List<Favourite> getByUser(int userId) {
        try {
            Query q = em.createNamedQuery("Favourite.findByUser", Favourite.class);
            q.setParameter("userId", userId);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Favourite> getFavourites() {
        return em.createNamedQuery("Favourite.findFavourites", Favourite.class).getResultList();
    }

    @Transactional
    public void saveFavourite(Favourite f) {
        if (f != null) {
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            em.flush();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteFavourite(int favouriteId) {
        Favourite f = em.find(Favourite.class, favouriteId);
        if (f != null)
            em.remove(f);
    }
}
