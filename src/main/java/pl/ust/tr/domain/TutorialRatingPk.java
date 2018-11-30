package pl.ust.tr.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TutorialRatingPk implements Serializable {

    @ManyToOne
    private Tutorial tutorial;

    @Column(insertable = false, updatable = false, nullable = false)
    private Integer userId;

    public TutorialRatingPk(Tutorial tutorial, Integer userId) {
        this.tutorial = tutorial;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TutorialRatingPk that = (TutorialRatingPk) o;
        return tutorial.equals(that.tutorial) &&
                userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tutorial, userId);
    }

    @Override
    public String toString() {
        return "TutorialRatingPk{" +
                "tutorial=" + tutorial +
                ", userId=" + userId +
                '}';
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public Integer getUserId() {
        return userId;
    }
}
