package pl.ust.tr.rating;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import pl.ust.tr.rating.Rating;
import pl.ust.tr.rating.RatingAssembler;
import pl.ust.tr.rating.RatingDto;
import pl.ust.tr.tutorial.Tutorial;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RatingAssemblerTest {

    private static final int RATING_ID = 555;
    private static final int USER = 1000;
    private static final int SCORE = 3;
    private static final String COMMENT = "comment";

    @Mock
    private RepositoryEntityLinks entityLinksMock;
    @InjectMocks
    private RatingAssembler assembler;

    @Test
    public void toResource() {
        Rating ratingMock = mock(Rating.class);
        Tutorial tutorialMock = mock(Tutorial.class);
        Link linkMock = mock(Link.class);
        
        when(ratingMock.getComment()).thenReturn(COMMENT);
        when(ratingMock.getScore()).thenReturn(SCORE);
        when(ratingMock.getUserId()).thenReturn(USER);
        when(ratingMock.getId()).thenReturn(RATING_ID);
        when(ratingMock.getTutorial()).thenReturn(tutorialMock);
        when(entityLinksMock.linkToSingleResource(any(Class.class), anyInt())).thenReturn(linkMock);
        when(linkMock.withRel(anyString())).thenReturn(linkMock);

        RatingDto dto = assembler.toResource(ratingMock);
        assertThat(dto.getLinks().size(), is(2));
    }
}