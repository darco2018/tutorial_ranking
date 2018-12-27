package pl.ust.tr.tutorial;

import org.junit.Test;
import pl.ust.tr.domain.Level;
import pl.ust.tr.domain.Medium;
import pl.ust.tr.skill.Skill;
import pl.ust.tr.tutorial.Tutorial;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class TutorialTest {
    @Test
    public void testConstructorAndGetters() throws Exception {
        Skill skill= new Skill("DO","Docker");
        Tutorial tutorial = new Tutorial("Master of Docker", "link to tutorial", "description",
                50, "6 hours", "keywords", skill , Medium.ARTICLE, Level.Beginner);
        assertNull(tutorial.getId());
        assertThat(tutorial.getTitle(), is("Master of Docker"));
        assertThat(tutorial.getLink(), is("link to tutorial"));
        assertThat(tutorial.getDescription(), is("description"));
        assertThat(tutorial.getPrice(), is(50));
        assertThat(tutorial.getDuration(), is("6 hours"));
        assertThat(tutorial.getKeywords(), is("keywords"));
        assertThat(tutorial.getSkill().getCode(), is("DO"));
        assertThat(tutorial.getMedium(), is(Medium.ARTICLE));
        assertThat(tutorial.getLevel(), is(Level.Beginner));

    }

    @Test
    public void equalsHashcodeVerify() {
        Skill skill= new Skill("CC","name");
        Tutorial tutorial1 = new Tutorial("Master of Docker", "link to tutorial", "description",
                50, "6 hours", "keywords", skill , Medium.ARTICLE, Level.Beginner);
        Tutorial tutorial2 = new Tutorial("Master of Docker", "link to tutorial", "description",
                50, "6 hours", "keywords", skill , Medium.ARTICLE, Level.Beginner);

        assertThat(tutorial1, is(tutorial2));
        assertThat(tutorial1.hashCode(), is(tutorial2.hashCode()));
    }

}