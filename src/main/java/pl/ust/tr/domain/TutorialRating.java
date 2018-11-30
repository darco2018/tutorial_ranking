package pl.ust.tr.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class TutorialRating {

    @EmbeddedId
    private TutorialRatingPk pk;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    public TutorialRating(TutorialRatingPk pk, Integer score, String comment) {
        this.pk = pk;
        this.score = score;
        this.comment = comment;
    }

    protected TutorialRating(){}

    @Override
    public String toString() {
        return "TutorialRating{" +
                "pk=" + pk +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }

    public TutorialRatingPk getPk() {
        return pk;
    }

    public void setPk(TutorialRatingPk pk) {
        this.pk = pk;
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
        return pk.equals(that.pk) &&
                score.equals(that.score) &&
                comment.equals(that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, score, comment);
    }
}
