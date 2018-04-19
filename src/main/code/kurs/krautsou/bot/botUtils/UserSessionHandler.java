package kurs.krautsou.bot.botUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import kurs.krautsou.dBUtil.userSession.UserSessionDao;
import kurs.krautsou.entity.SessionsEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class UserSessionHandler {

    @Autowired
    UserSessionDao userSessionDao;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");

    public boolean sessionIsActive(Long userId){
        return userSessionDao.get(userId) != null;
    }

    public void createUserSession(Long userId, String questionAndAnswer){
        String [] questionAndAnswerArray = questionAndAnswer.split("\\|");
        String question = questionAndAnswerArray[0];
        String answer = questionAndAnswerArray[1];
        LocalDateTime dateTime = LocalDateTime.now();
        userSessionDao.save(new SessionsEntity(userId, dateTime.format(formatter), question, answer));
    }

    public String getAnswerFromSession(long userId){
        SessionsEntity userSession = userSessionDao.get(userId);
        return userSession.getAnswer();
    }

    public void deleteUserSession(long userId){
        userSessionDao.delete(userSessionDao.get(userId));
    }

    private String getDateFromSession(long userId){
        SessionsEntity userSession = userSessionDao.get(userId);
        return userSession.getStartTime();
    }

    public boolean validateDate(LocalDateTime currentDate, long userId) {
        LocalDateTime dateTimeFromSession = LocalDateTime.parse(getDateFromSession(userId), formatter);
        return currentDate.isBefore(dateTimeFromSession.plusSeconds(20));
    }
}
