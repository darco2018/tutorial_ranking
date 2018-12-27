package pl.ust.tr.rating;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ust.tr.rating.Rating;
import pl.ust.tr.rating.RatingDto;
import pl.ust.tr.rating.RatingService;
import pl.ust.tr.tutorial.Tutorial;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Invoke the Controller methods via HTTP.
 * Do not invoke the tutorialRatingService methods, use Mock instead
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RatingControllerTest {
    private static final String RATINGS_URL = "/ratings";

    //These Tutorial and rating id's do not exist in the db
    private static final int TOUR_ID = 999;
    private static final int RATING_ID = 555;
    private static final int CUSTOMER_ID = 1000;
    private static final int SCORE = 3;
    private static final String COMMENT = "comment";

    @MockBean
    private RatingService ratingServiceMock;

    @Mock
    private Rating tRating;

    @Mock
    private Tutorial tutorial;

    @Autowired
    private TestRestTemplate restTemplate;


    @Before
    public void setupReturnValuesOfMockMethods() {
        when(tRating.getTutorial()).thenReturn(tutorial);
        when(tutorial.getId()).thenReturn(TOUR_ID);
        when(tRating.getComment()).thenReturn(COMMENT);
        when(tRating.getScore()).thenReturn(SCORE);
        when(tRating.getUserId()).thenReturn(CUSTOMER_ID);
    }


    /**
     *  HTTP GET /ratings
     */
    @Test
    public void getRatings() {

        when(ratingServiceMock.lookupAll()).thenReturn(Arrays.asList(tRating, tRating, tRating));

        ResponseEntity<List<RatingDto>> response = restTemplate.exchange(RATINGS_URL,
                                                                        HttpMethod.GET,null,
                                                                        new ParameterizedTypeReference<List<RatingDto>>() {});

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().size(), is(3));
    }

    /**
     *  HTTP GET /ratings/{id}
     */
    @Test
    public void getOne()  {

        when(ratingServiceMock.lookupRatingById(RATING_ID)).thenReturn(Optional.of(tRating));

        ResponseEntity<RatingDto> response =
                restTemplate.getForEntity(RATINGS_URL + "/" + RATING_ID, RatingDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getUserId(), is(CUSTOMER_ID));
        assertThat(response.getBody().getComment(), is(COMMENT));
        assertThat(response.getBody().getScore(), is(SCORE));
    }
}