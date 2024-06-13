package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;


public class HeladeraJPARepository {


    @Getter @Setter
    private EntityManager entityManager;
    @Setter
    private EntityManagerFactory entityManagerFactory;

    @Getter
    private Collection<Heladera> heladeras;

    public HeladeraJPARepository() {
        this.heladeras = new ArrayList<>();
    }

    public HeladeraJPARepository(EntityManager entityManager, EntityManagerFactory entityManagerFactory) {
        super();
        this.entityManager = entityManager;
        this.entityManagerFactory = entityManagerFactory;
    }
    public Heladera save(Heladera heladera) {
        //entityManager.getTransaction().begin();
        entityManager.persist(heladera);
        //entityManager.getTransaction().commit();
        //entityManager.close();
        return heladera;
    }

    public Heladera findById(Integer id) {
        Heladera heladera = this.entityManager.find(Heladera.class, id);
        if (heladera == null) {
            throw new NoSuchElementException();
        }
        return heladera;
    }
    
    public Collection<Heladera> all() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Heladera> criteriaQuery = criteriaBuilder.createQuery(Heladera.class);
        Root<Heladera> root = criteriaQuery.from(Heladera.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public void guardarVianda(Heladera heladera, String qrVianda){
        EntityManager em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        heladera.guardar(qrVianda);
        //em.persist(heladera);
        em.getTransaction().commit();
        em.close();
    }

}

