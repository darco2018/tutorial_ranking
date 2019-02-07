package pl.ust.tr.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class MediumConverterTest {
    private MediumConverter converter = new MediumConverter();
    @Test
    public void convertToDatabaseColumn() throws Exception {
        assertThat(converter.convertToDatabaseColumn(Medium.ARTICLE), is(Medium.ARTICLE.toDbValue()));
    }



    @Test
    public void convertToEntityAttribute() throws Exception {
        assertThat(converter.convertToEntityAttribute(Medium.ARTICLE.toDbValue()), is(Medium.ARTICLE));
    }

}