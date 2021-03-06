package pl.ust.tr.rating;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.ust.tr.rating.Rating;
import pl.ust.tr.rating.RatingService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RatingServiceIntegrationTest {
    private static final int USER_ID = 456;
    private static final int TUTORIAL_ID = 1;
    private static final int NOT_EXISTING_TUTORIAL_ID = 123;

    @Autowired
    private RatingService service;

    //Happy Path delete existing Rating.
    @Test
    public void delete() {
        List<Rating> ratings = service.lookupAll();
        service.delete(ratings.get(0).getTutorial().getId(), ratings.get(0).getUserId());
        assertThat(service.lookupAll().size(), is(ratings.size() - 1));
    }

    //UnHappy Path, Tutorial NOT_EXISTING_TUTORIAL_ID does not exist
    @Test(expected = NoSuchElementException.class)
    public void deleteException() {
        service.delete(NOT_EXISTING_TUTORIAL_ID, 1234);
    }


    //Happy Path to Create a new Tutorial Rating
    @Test
    public void createNew() {
        //would throw NoSuchElementException if Rating for TUTORIAL_ID by USER_ID already exists
        service.createNew(TUTORIAL_ID, USER_ID, 2, "it was fair");

        //Verify New Tutorial Rating created.
        Rating newRating = service.verifyTutorialRating(TUTORIAL_ID, USER_ID);
        assertThat(newRating.getTutorial().getId(), is(TUTORIAL_ID));
        assertThat(newRating.getUserId(), is(USER_ID));
        assertThat(newRating.getScore(), is(2));
        assertThat(newRating.getComment(), is ("it was fair"));
    }

    //UnHappy Path, Tutorial NOT_EXISTING_TUTORIAL_ID does not exist
    @Test(expected = NoSuchElementException.class)
    public void createNewException() {
        service.createNew(NOT_EXISTING_TUTORIAL_ID, USER_ID, 2, "it was fair");
    }

    //Happy Path many customers Rate one tutorial
    @Test
    public void rateMany() {
        int ratings = service.lookupAll().size();
        service.rateMany(TUTORIAL_ID, 5, new Integer[]{100, 101, 102});
        assertThat(service.lookupAll().size(), is(ratings + 3));
    }

    //Unhappy Path, 2nd Invocation would create duplicates in the database, DataIntegrityViolationException thrown
    @Test(expected = DataIntegrityViolationException.class)
    public void rateManyProveRollback() {
        int ratings = service.lookupAll().size();
        Integer customers[] = {100, 101, 102};
        service.rateMany(TUTORIAL_ID, 3, customers);
        service.rateMany(TUTORIAL_ID, 3, customers);
    }

    //Happy Path, Update a Tutorial Rating already in the database
    @Test
    public void update() {
        createNew();
        Rating rating = service.update(TUTORIAL_ID, USER_ID, 1, "one");
        assertThat(rating.getTutorial().getId(), is(TUTORIAL_ID));
        assertThat(rating.getUserId(), is(USER_ID));
        assertThat(rating.getScore(), is(1));
        assertThat(rating.getComment(), is("one"));
    }

    //Unhappy path, no Tutorial Rating exists for tutorialId=1 and customer=1
    @Test(expected = NoSuchElementException.class)
    public void updateException() throws Exception {
        service.update(12345, 54321, 1, "one");
    }

    //Happy Path, Update a Tutorial Rating already in the database
    @Test
    public void updateSome() {
        createNew();
        Rating rating = service.update(TUTORIAL_ID, USER_ID, 1, "one");
        assertThat(rating.getTutorial().getId(), is(TUTORIAL_ID));
        assertThat(rating.getUserId(), is(USER_ID));
        assertThat(rating.getScore(), is(1));
        assertThat(rating.getComment(), is("one"));
    }

    //Unhappy path, no Tutorial Rating exists for tutorialId=1 and customer=1
    @Test(expected = NoSuchElementException.class)
    public void updateSomeException() throws Exception {
        service.update(12345, 54321, 1, "one");
    }

    //Happy Path get average score of a Tutorial.
    @Test
    public void getAverageScore() {
        assertTrue(service.getAverageScore(TUTORIAL_ID) == 5.0);
    }

    //UnHappy Path, Tutorial NOT_EXISTING_TUTORIAL_ID does not exist
    @Test(expected = NoSuchElementException.class)
    public void getAverageScoreException() {
        service.getAverageScore(NOT_EXISTING_TUTORIAL_ID); //That tutorial does not exist
    }
}