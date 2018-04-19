package kurs.krautsou.dBUtil.userScore;

import org.springframework.stereotype.Component;
import kurs.krautsou.entity.UserScoreEntity;

import java.util.List;

@Component
public interface UserScoreDao {

    UserScoreEntity get(long id);

    List getAll();

    long save(UserScoreEntity userScore);

    void update(UserScoreEntity userScore);
}
