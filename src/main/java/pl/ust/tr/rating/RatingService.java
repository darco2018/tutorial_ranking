package pl.ust.tr.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.ust.tr.rating.Rating;
import pl.ust.tr.tutorial.Tutorial;
import pl.ust.tr.rating.RatingRepository;
import pl.ust.tr.tutorial.TutorialRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@Transactional
public class RatingService {
    private RatingRepository ratingRepository;
    private TutorialRepository tutorialRepository;

   
    @Autowired
    public RatingService(RatingRepository ratingRepository, TutorialRepository tutorialRepository) {
        this.ratingRepository = ratingRepository;
        this.tutorialRepository = tutorialRepository;
    }
    
    public void createNew(int tutorialId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        ratingRepository.save(new Rating(verifyTutorial(tutorialId), customerId,
                score, comment));
    }

    public Optional<Rating> lookupRatingById(int id){
        return ratingRepository.findById(id);
    }

    public List<Rating> lookupAll(){
        return ratingRepository.findAll();
    }
    
    public Page<Rating> lookupRatings(int tutorialId, Pageable pageable) throws NoSuchElementException  {
        return ratingRepository.findByTutorialId(verifyTutorial(tutorialId).getId(), pageable);
    }
   
    public Rating update(int tutorialId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        Rating rating = verifyTutorialRating(tutorialId, customerId);
        rating.setScore(score);
        rating.setComment(comment);
        return ratingRepository.save(rating);
    }

    
    public Rating updateSome(int tutorialId, Integer customerId, Integer score, String comment)
            throws NoSuchElementException {
        Rating rating = verifyTutorialRating(tutorialId, customerId);
        if (score != null) {
            rating.setScore(score);
        }
        if (comment!= null) {
            rating.setComment(comment);
        }
        return ratingRepository.save(rating);
    }
    
    public void delete(int tutorialId, Integer customerId) throws NoSuchElementException {
        Rating rating = verifyTutorialRating(tutorialId, customerId);
        ratingRepository.delete(rating);
    }
   
    public Double getAverageScore(int tutorialId)  throws NoSuchElementException  {
        List<Rating> ratings = ratingRepository.findByTutorialId(verifyTutorial(tutorialId).getId());
        OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
        return average.isPresent() ? average.getAsDouble():null;
    }
    
    public void rateMany(int tutorialId,  int score, Integer [] customers) {
        tutorialRepository.findById(tutorialId).ifPresent(tutorial -> {
            for (Integer c : customers) {
                ratingRepository.save(new Rating(tutorial, c, score));
            }
        });
    }
   
    private Tutorial verifyTutorial(int tutorialId) throws NoSuchElementException {
        return tutorialRepository.findById(tutorialId).orElseThrow(() ->
                new NoSuchElementException("Tutorial does not exist " + tutorialId)
        );
    }
    
    public Rating verifyTutorialRating(int tutorialId, int customerId) throws NoSuchElementException {
        return ratingRepository.findByTutorialIdAndUserId(tutorialId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tutorial-Rating pair for request("
                        + tutorialId + " for customer" + customerId));
    }


}
