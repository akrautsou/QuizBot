package kurs.krautsou.bot.botUtils;

import kurs.krautsou.entity.QuestionsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import kurs.krautsou.dBUtil.questionAndAnswer.QuestionAndAnswerDao;

@Component
public class QuestionAnswerGenerator {

    @Autowired
    private QuestionAndAnswerDao questionAndAnswerDao;

    private long getRandomNumber(){
        return (long) (Math.random() * questionAndAnswerDao.getMaximumId() + 1);
    }

    private QuestionsEntity getRandomQuestionAndAnswer(){
        QuestionsEntity questionAndAnswer = null;

        while(questionAndAnswer == null){
            questionAndAnswer = questionAndAnswerDao.get(getRandomNumber());
        }

        return questionAndAnswer;
    }

    public String getNewQuestionAndAnswerForUser(){
        StringBuilder sb = new StringBuilder();
        QuestionsEntity questionAndAnswer = getRandomQuestionAndAnswer();
        sb.append(questionAndAnswer.getQuestion()).append("|").append(questionAndAnswer.getAnswer());
        return sb.toString();
    }

}
