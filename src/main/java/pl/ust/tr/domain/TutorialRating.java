package pl.ust.tr.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TutorialRating")
public class TutorialRating implements Serializable {
    //----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tutorial_id")
    private Tutorial tutorial;

    @Column(name = "user_id")
    private Integer userId;
    // ---------------------

    @Column(columnDefinition = "smallint(2) unsigned", nullable = false, precision=2, scale=0)
    private Integer score;

    @Column(length=255)
    private String comment;

    public TutorialRating(Tutorial tutorial, Integer userId, Integer score, String comment) {
        this.tutorial = tutorial;
        this.userId = userId;
        this.score = score;
        this.comment = comment;
    }

    public TutorialRating(Tutorial tutorial, Integer userId, Integer score) {
        this.tutorial = tutorial;
        this.userId = userId;
        this.score = score;
        this.comment = this.toComment(score);
    }

    protected TutorialRating(){}

    /**
     * Auto Generate a message for a score.
    */
    private String toComment(Integer score) {
        switch (score) {
            case 1:return "Terrible";
            case 2:return "Poor";
            case 3:return "Fair";
            case 4:return "Good";
            case 5:return "Great";
            default: return score.toString();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TutorialRating that = (TutorialRating) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tutorial, that.tutorial) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(score, that.score) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tutorial, userId, score, comment);
    }

    @Override
    public String toString() {
        return "TutorialRating{" +
                "id=" + id +
                ", tutorial=" + tutorial +
                ", userId=" + userId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }
}
