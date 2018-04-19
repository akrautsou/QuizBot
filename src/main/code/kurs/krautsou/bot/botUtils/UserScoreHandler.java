package kurs.krautsou.bot.botUtils;

import kurs.krautsou.dBUtil.userScore.UserScoreDao;
import kurs.krautsou.exceptions.UserException;
import kurs.krautsou.entity.UserScoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserScoreHandler {

    @Autowired
    UserScoreDao userScoreDao;

    public boolean userAlreadyInChart(long userId){
        return userScoreDao.get(userId) != null;
    }

    public void addNewUserInChart(long userId, String userName){
        userScoreDao.save(new UserScoreEntity(userId, userName, 0));
    }

    public long incrementUserScore(long userId){
        UserScoreEntity userScore = userScoreDao.get(userId);
        userScore.setScore(userScore.getScore() + 1);
        userScoreDao.update(userScore);
        return userScore.getScore();
    }

    public long getUserScoreById(long userId){
        return userScoreDao.get(userId).getScore();
    }

    public List<UserScoreEntity> getTopFiveUserScore() {
        ArrayList<UserScoreEntity> userScoreArrayList = new ArrayList<>(userScoreDao.getAll());

        if(userScoreArrayList.isEmpty()){
            throw new UserException("User score list is empty.");
        }

        Collections.sort(userScoreArrayList);

        int userScoreListSize = userScoreArrayList.size();

        List<UserScoreEntity> topScoreList;

        if(userScoreListSize < 5){
            topScoreList = new ArrayList<>(userScoreArrayList.subList(0, userScoreListSize));
        } else{
            topScoreList = new ArrayList<>(userScoreArrayList.subList(0, 5));
        }

        return topScoreList;

    }

}
