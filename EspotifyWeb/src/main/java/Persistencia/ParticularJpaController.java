/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Logica.PartId;
import Logica.Particular;
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
public class ParticularJpaController implements Serializable {

    public ParticularJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ParticularJpaController(){
        this.emf = Persistence.createEntityManagerFactory("EspotifyWeb");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Particular particular) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(particular);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParticular(particular.getNombre(),particular.getCliente().getNickname()) != null) {
                throw new PreexistingEntityException("Particular " + particular + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Particular particular) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            particular = em.merge(particular);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = particular.getNombre();
                if (findParticular(id,particular.getCliente().getNickname()) == null) {
                    throw new NonexistentEntityException("The particular with id " + id + " no longer exists.");
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
            Particular particular;
            try {
                particular = em.getReference(Particular.class, id);
                particular.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The particular with id " + id + " no longer exists.", enfe);
            }
            em.remove(particular);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Particular> findParticularEntities() {
        return findParticularEntities(true, -1, -1);
    }

    public List<Particular> findParticularEntities(int maxResults, int firstResult) {
        return findParticularEntities(false, maxResults, firstResult);
    }

    private List<Particular> findParticularEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Particular.class));
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

    public Particular findParticular(String nombre, String cliente) {
        EntityManager em = getEntityManager();
        try {
            // Crear un objeto de la clave compuesta
            PartId partId = new PartId(nombre, cliente);

            // Buscar usando la clave compuesta
            return em.find(Particular.class, partId);
        } finally {
            em.close();
        }
    }

    public int getParticularCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Particular> rt = cq.from(Particular.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
