package dao;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class EntityDAOJPA<T> implements EntityDAO<T> {

    @Inject
    protected EntityManager em;

    @Inject
    protected Event<T> propEventSrc;

    public void create(T t) {

        em.persist(t);

        propEventSrc.fire(t);
    }

    public void delete(T t) {

        em.remove(em.contains(t) ? t : em.merge(t));

        propEventSrc.fire(t);
    }

    public void update(T t) {

        em.merge(t);

        propEventSrc.fire(t);

    }
}
