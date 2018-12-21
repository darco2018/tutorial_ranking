package pl.ust.tr.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.ust.tr.domain.Tutorial;
import pl.ust.tr.domain.TutorialRating;
import pl.ust.tr.repository.TutorialRatingRepository;
import pl.ust.tr.repository.TutorialRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TutorialRatingServiceTest {

    private static final int USER_ID = 123;
    private static final int TUTORIAL_ID = 1;
    private static final int TUTORIAL_RATING_ID = 100;

    @Mock
    private TutorialRepository tutorialRepository;
    @Mock
    private TutorialRatingRepository tRatingRepository;

    @InjectMocks //Autowire TutorialRatingService(tRatingRepository, tutorialRepository)
    private TutorialRatingService service;

    @Mock
    private Tutorial tutorial;
    @Mock
    private TutorialRating tRating;


    /**
     * Mock responses to commonly invoked methods.
     */
    @Before
    public void setupReturnValuesOfMockMethods() {
        when(tutorialRepository.findById(TUTORIAL_ID)).thenReturn(Optional.of(tutorial));
        when(tutorial.getId()).thenReturn(TUTORIAL_ID);
        when(tRatingRepository.findByTutorialIdAndUserId(TUTORIAL_ID,USER_ID)).thenReturn(Optional.of(tRating));
        when(tRatingRepository.findByTutorialId(TUTORIAL_ID)).thenReturn(Arrays.asList(tRating));
    }

    /**************************************************************************************
     *
     * Verify the service's return values
     *
     **************************************************************************************/
    @Test
    public void lookupRatingById() {
        when(tRatingRepository.findById(TUTORIAL_RATING_ID)).thenReturn(Optional.of(tRating));

        //invoke and verify lookupRatingById
        assertThat(service.lookupRatingById(TUTORIAL_RATING_ID).get(), is(tRating));
    }

    @Test
    public void lookupAll() {
        when(tRatingRepository.findAll()).thenReturn(Arrays.asList(tRating));

        //invoke and verify lookupAll
        assertThat(service.lookupAll().get(0), is(tRating));
    }

    @Test
    public void getAverageScore() {
        //when(tRatingRepository.findByTutorialId(TUTORIAL_ID)).thenReturn(Arrays.asList(tRating));
        when(tRating.getScore()).thenReturn(5);

        //invoke and verify getAverageScore
        assertThat(service.getAverageScore(TUTORIAL_ID), is(5.0));
    }

    @Test
    public void lookupRatings() {
        //create mocks of Pageable and Page (only needed in this test)
        Pageable pageable = mock(Pageable.class);
        Page page = mock(Page.class);
        when(tRatingRepository.findByTutorialId(1, pageable)).thenReturn(page);

        //invoke and verify lookupRatings
        assertThat(service.lookupRatings(TUTORIAL_ID, pageable), is(page));
    }

    /**************************************************************************************
     *
     * Verify the invocation of dependencies.
     *
     **************************************************************************************/

    @Test
    public void delete() {
        //invoke delete
        service.delete(TUTORIAL_ID,USER_ID);

        //verify tutorialRatingRepository.delete invoked
        verify(tRatingRepository).delete(any(TutorialRating.class));
    }

    @Test
    public void rateMany() {
        //invoke rateMany
        service.rateMany(TUTORIAL_ID, 10, new Integer[]{USER_ID, USER_ID + 1});

        //verify tutorialRatingRepository.save invoked twice
        verify(tRatingRepository, times(2)).save(any(TutorialRating.class));
    }

    @Test
    public void update() {
        //invoke update
        service.update(TUTORIAL_ID,USER_ID,5, "great");

        //verify tutorialRatingRepository.save invoked once
        verify(tRatingRepository).save(any(TutorialRating.class));

        //verify and tutorialRating setter methods invoked
        verify(tRating).setComment("great");
        verify(tRating).setScore(5);
    }

    @Test
    public void updateSome() {
        //invoke updateSome
        service.updateSome(TUTORIAL_ID, USER_ID, 1, "awful");

        //verify tutorialRatingRepository.save invoked once
        verify(tRatingRepository).save(any(TutorialRating.class));

        //verify and tutorialRating setter methods invoked
        verify(tRating).setComment("awful");
        verify(tRating).setScore(1);
    }

     /**************************************************************************************
     *
     * Verify the invocation of dependencies
     * Capture parameter values.
     * Verify the parameters.
     *
     **************************************************************************************/

     @Test
    public void createNew() {
        //prepare to capture a TutorialRating Object
        ArgumentCaptor<TutorialRating> tutorialRatingCaptor = ArgumentCaptor.forClass(TutorialRating.class);

        //invoke createNew
        service.createNew(TUTORIAL_ID, USER_ID, 2, "ok");

        //verify tutorialRatingRepository.save invoked once and capture the TutorialRating Object
        verify(tRatingRepository).save(tutorialRatingCaptor.capture());

        //verify the attributes of the Tutorial Rating Object
        assertThat(tutorialRatingCaptor.getValue().getTutorial(), is(tutorial));
        assertThat(tutorialRatingCaptor.getValue().getUserId(), is(USER_ID));
        assertThat(tutorialRatingCaptor.getValue().getScore(), is(2));
        assertThat(tutorialRatingCaptor.getValue().getComment(), is("ok"));
    }
}