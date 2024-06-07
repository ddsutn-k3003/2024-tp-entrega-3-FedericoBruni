package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;


public class HeladeraJPARepository {


    @Getter @Setter
    private EntityManager entityManager;

    @Getter
    private Collection<Heladera> heladeras;

    public HeladeraJPARepository() {
        this.heladeras = new ArrayList<>();
    }

    public HeladeraJPARepository(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }
    public Heladera save(Heladera heladera) {
        this.entityManager.persist(heladera);
        return heladera;
    }

    public Heladera findById(Integer id) {
        // return this.entityManager.find(Heladera.class, id);
        Heladera heladera = this.entityManager.find(Heladera.class, id);
        if (heladera == null) throw new NoSuchElementException();
        return heladera;
    }
    
    public Collection<Heladera> all() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Heladera> criteriaQuery = criteriaBuilder.createQuery(Heladera.class);
        Root<Heladera> root = criteriaQuery.from(Heladera.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}

