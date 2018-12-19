package pl.ust.tr.domain;

import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;


public class TutorialRatingTest {

    private Tutorial tutorial = new Tutorial("Master of Docker", "link to tutorial", "description",
            50, "6 hours", "keywords", new Skill("DO","Docker") , Medium.ARTICLE, Level.Beginner);

    @Test
    public void testConstructor1() throws Exception {
        TutorialRating rating = new TutorialRating(tutorial, 22, 1, "comment");
        testIt(rating);
        assertThat(rating.getComment(), is("comment"));
    }
    @Test
    public void testConstructor2() throws Exception {
        TutorialRating rating = new TutorialRating(tutorial, 22, 1);
        testIt(rating);
        assertThat(rating.getComment(), is("Terrible"));
    }

    private void testIt(TutorialRating rating){
        assertThat(rating.getId(), is(nullValue()));
        assertThat(rating.getTutorial(), is(tutorial));
        assertThat(rating.getScore(), is(1));
        assertThat(rating.getUserId(), is(22));
    }

    @Test
    public void equalsHashcodeVerify() {
        TutorialRating rating1 = new TutorialRating(tutorial, 1, 1, "comment");
        TutorialRating rating2 = new TutorialRating(tutorial, 1, 1, "comment");

        assertEquals(rating1,rating2);
        assertEquals(rating1.hashCode(), rating2.hashCode());
    }
}