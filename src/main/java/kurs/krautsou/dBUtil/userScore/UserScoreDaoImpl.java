package kurs.krautsou.dBUtil.userScore;

import org.springframework.stereotype.Repository;
import kurs.krautsou.entity.UserScoreEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserScoreDaoImpl implements UserScoreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserScoreEntity get(long id) {
        return entityManager.find(UserScoreEntity.class, id);
    }

    @Override
    public List getAll() {
        return entityManager.createQuery("from UserScoreEntity").getResultList();
    }

    @Override
    public long save(UserScoreEntity userScore) {
        entityManager.persist(userScore);
        return userScore.getId();
    }

    @Override
    public void update(UserScoreEntity userScore) {
        entityManager.merge(userScore);
    }
}
