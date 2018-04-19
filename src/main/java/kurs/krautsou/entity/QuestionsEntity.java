package kurs.krautsou.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "questions", schema = "main")
public class QuestionsEntity implements Serializable {
    private static final long serialVersionUID = -4333504559230040445L;
    private long id;
    private String question;
    private String answer;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "question", nullable = true, length = 50)
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Basic
    @Column(name = "answer", nullable = true, length = 50)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionsEntity that = (QuestionsEntity) o;

        return id == that.id && (question != null ? question.equals(that.question) : that.question == null) && (answer != null ? answer.equals(that.answer) : that.answer == null);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}
