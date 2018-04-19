package kurs.krautsou.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_score", schema = "main")
public class UserScoreEntity implements Serializable, Comparable<UserScoreEntity> {
    private static final long serialVersionUID = -4816217686655101674L;
    private long id;
    private String userName;
    private long score;

    public UserScoreEntity(long id, String userName, long score) {
        this.id = id;
        this.userName = userName;
        this.score = score;
    }

    public UserScoreEntity() {
    }

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userName", nullable = true, length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "score", nullable = true, length = -1)
    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    @Override
    public int compareTo(UserScoreEntity userScore) {
        return (int) (this.score - userScore.getScore());
    }

    @Override
    public String toString() {
        return "UserScoreEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", score=" + score +
                '}';
    }
}
