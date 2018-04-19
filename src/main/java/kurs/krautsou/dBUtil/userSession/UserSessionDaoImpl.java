package kurs.krautsou.dBUtil.userSession;

import kurs.krautsou.entity.SessionsEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserSessionDaoImpl implements UserSessionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long save(SessionsEntity userSession) {
        entityManager.persist(userSession);
        return userSession.getId();
    }

    @Override
    public SessionsEntity get(long id) {
        return entityManager.find(SessionsEntity.class, id);
    }

    @Override
    public List list() {
        return entityManager.createQuery("from SessionsEntity ").getResultList();
    }

    @Override
    public void update(long id, SessionsEntity userSession) {
        entityManager.merge(userSession);
    }

    @Override
    public void delete(SessionsEntity userSession) {
        if(entityManager.contains(userSession)){
            entityManager.remove(userSession);
        } else{
            entityManager.remove(entityManager.merge(userSession));
        }
    }
}
