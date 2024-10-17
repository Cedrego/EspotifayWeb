/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Logica.porDefecto;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author cedre
 */
public class porDefectoJpaController implements Serializable {

    public porDefectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public porDefectoJpaController(){
        this.emf = Persistence.createEntityManagerFactory("EspotifyWeb");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(porDefecto porDefecto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(porDefecto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findporDefecto(porDefecto.getNombre()) != null) {
                throw new PreexistingEntityException("porDefecto " + porDefecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(porDefecto porDefecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            porDefecto = em.merge(porDefecto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = porDefecto.getNombre();
                if (findporDefecto(id) == null) {
                    throw new NonexistentEntityException("The porDefecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            porDefecto porDefecto;
            try {
                porDefecto = em.getReference(porDefecto.class, id);
                porDefecto.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The porDefecto with id " + id + " no longer exists.", enfe);
            }
            em.remove(porDefecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<porDefecto> findporDefectoEntities() {
        return findporDefectoEntities(true, -1, -1);
    }

    public List<porDefecto> findporDefectoEntities(int maxResults, int firstResult) {
        return findporDefectoEntities(false, maxResults, firstResult);
    }

    private List<porDefecto> findporDefectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(porDefecto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public porDefecto findporDefecto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(porDefecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getporDefectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<porDefecto> rt = cq.from(porDefecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
