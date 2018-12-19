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
    private TutorialRepository tutorialRepositoryMock;
    @Mock
    private TutorialRatingRepository tutorialRatingRepositoryMock;

    @InjectMocks //Autowire TutorialRatingService(tutorialRatingRepositoryMock, tutorialRepositoryMock)
    private TutorialRatingService service;

    @Mock
    private Tutorial tutorialMock;
    @Mock
    private TutorialRating tutorialRatingMock;


    /**
     * Mock responses to commonly invoked methods.
     */
    @Before
    public void setupReturnValuesOfMockMethods() {
        when(tutorialRepositoryMock.findById(TUTORIAL_ID)).thenReturn(Optional.of(tutorialMock));
        when(tutorialMock.getId()).thenReturn(TUTORIAL_ID);
        when(tutorialRatingRepositoryMock.findByTutorialIdAndUserId(TUTORIAL_ID,USER_ID)).thenReturn(Optional.of(tutorialRatingMock));
        when(tutorialRatingRepositoryMock.findByTutorialId(TUTORIAL_ID)).thenReturn(Arrays.asList(tutorialRatingMock));
    }

    /**************************************************************************************
     *
     * Verify the service's return values
     *
     **************************************************************************************/
    @Test
    public void lookupRatingById() {
        when(tutorialRatingRepositoryMock.findById(TUTORIAL_RATING_ID)).thenReturn(Optional.of(tutorialRatingMock));

        //invoke and verify lookupRatingById
        assertThat(service.lookupRatingById(TUTORIAL_RATING_ID).get(), is(tutorialRatingMock));
    }

    @Test
    public void lookupAll() {
        when(tutorialRatingRepositoryMock.findAll()).thenReturn(Arrays.asList(tutorialRatingMock));

        //invoke and verify lookupAll
        assertThat(service.lookupAll().get(0), is(tutorialRatingMock));
    }

    @Test
    public void getAverageScore() {
        when(tutorialRatingMock.getScore()).thenReturn(10);

        //invoke and verify getAverageScore
        assertThat(service.getAverageScore(TUTORIAL_ID), is(10.0));
    }

    @Test
    public void lookupRatings() {
        //create mocks of Pageable and Page (only needed in this test)
        Pageable pageable = mock(Pageable.class);
        Page page = mock(Page.class);
        when(tutorialRatingRepositoryMock.findByTutorialId(1, pageable)).thenReturn(page);

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
        service.delete(1,USER_ID);

        //verify tutorialRatingRepository.delete invoked
        verify(tutorialRatingRepositoryMock).delete(any(TutorialRating.class));
    }

    @Test
    public void rateMany() {
        //invoke rateMany
        service.rateMany(TUTORIAL_ID, 10, new Integer[]{USER_ID, USER_ID + 1});

        //verify tutorialRatingRepository.save invoked twice
        verify(tutorialRatingRepositoryMock, times(2)).save(any(TutorialRating.class));
    }

    @Test
    public void update() {
        //invoke update
        service.update(TUTORIAL_ID,USER_ID,5, "great");

        //verify tutorialRatingRepository.save invoked once
        verify(tutorialRatingRepositoryMock).save(any(TutorialRating.class));

        //verify and tutorialRating setter methods invoked
        verify(tutorialRatingMock).setComment("great");
        verify(tutorialRatingMock).setScore(5);
    }

    @Test
    public void updateSome() {
        //invoke updateSome
        service.updateSome(TUTORIAL_ID, USER_ID, 1, "awful");

        //verify tutorialRatingRepository.save invoked once
        verify(tutorialRatingRepositoryMock).save(any(TutorialRating.class));

        //verify and tutorialRating setter methods invoked
        verify(tutorialRatingMock).setComment("awful");
        verify(tutorialRatingMock).setScore(1);
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
        verify(tutorialRatingRepositoryMock).save(tutorialRatingCaptor.capture());

        //verify the attributes of the Tutorial Rating Object
        assertThat(tutorialRatingCaptor.getValue().getTutorial(), is(tutorialMock));
        assertThat(tutorialRatingCaptor.getValue().getUserId(), is(USER_ID));
        assertThat(tutorialRatingCaptor.getValue().getScore(), is(2));
        assertThat(tutorialRatingCaptor.getValue().getComment(), is("ok"));
    }
}