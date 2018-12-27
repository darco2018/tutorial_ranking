package pl.ust.tr.skill;

import org.junit.Test;
import pl.ust.tr.skill.Skill;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class SkillTest {
    @Test
    public void testConstructorAndGetters() throws Exception {
        Skill skill= new Skill("DO","name");
        assertThat(skill.getName(), is("name"));
        assertThat(skill.getCode(), is("DO"));
    }

    @Test
    public void equalsHashcodeVerify() {
        Skill skill1 = new Skill("DO","name");
        Skill skill2 = new Skill("DO","name");

        assertThat(skill1,is(skill2));
        assertThat(skill2.hashCode(), is(skill2.hashCode()));
    }
}