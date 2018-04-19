package kurs.krautsou.dBUtil.questionAndAnswer;

import kurs.krautsou.entity.QuestionsEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QuestionAndAnswerDao {

    QuestionsEntity get(long id);

    List getAll();

    long getRowCount();

    long getMaximumId();

}
