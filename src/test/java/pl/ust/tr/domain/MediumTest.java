package pl.ust.tr.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class MediumTest {
    @Test
    public void findByLabel() throws Exception {
        assertThat(Medium.VIDEO, is(Medium.fromDbValue("Video")));
        assertThat(Medium.ARTICLE, is(Medium.fromDbValue("Article")));
        assertThat(Medium.COURSE, is(Medium.fromDbValue("Course")));
    }

    @Test
    public void getLabel() throws Exception {
        assertThat(Medium.VIDEO.getValueForDb(), is("Video"));
        assertThat(Medium.ARTICLE.getValueForDb(), is("Article"));
        assertThat(Medium.COURSE.getValueForDb(), is("Course"));
    }

}