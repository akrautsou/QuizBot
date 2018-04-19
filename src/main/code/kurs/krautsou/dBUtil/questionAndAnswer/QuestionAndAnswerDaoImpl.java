package kurs.krautsou.dBUtil.questionAndAnswer;

import kurs.krautsou.entity.QuestionsEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class QuestionAndAnswerDaoImpl implements QuestionAndAnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public QuestionsEntity get(long id) {
        return entityManager.find(QuestionsEntity.class, id);
    }

    @Override
    public List getAll() {
        return entityManager.createQuery("from QuestionsEntity ").getResultList();
    }

    @Override
    public long getRowCount() {
        return getAll().size();
    }

    @Override
    public long getMaximumId() {
        List<QuestionsEntity> questionAndAnswersList = getAll();
        return questionAndAnswersList.get(questionAndAnswersList.size() - 1).getId();
    }
}
