package kurs.krautsou.dBUtil.userSession;

import kurs.krautsou.entity.SessionsEntity;

import java.util.List;

public interface UserSessionDao {

    long save(SessionsEntity userSession);

    SessionsEntity get(long id);

    List list();

    void update(long id, SessionsEntity userSession);

    void delete(SessionsEntity userSession);


}
