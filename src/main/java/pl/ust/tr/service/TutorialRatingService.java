package pl.ust.tr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.ust.tr.domain.Tutorial;
import pl.ust.tr.domain.TutorialRating;
import pl.ust.tr.repository.TutorialRatingRepository;
import pl.ust.tr.repository.TutorialRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;

@Service
@Transactional
public class TutorialRatingService {
    private TutorialRatingRepository tutorialRatingRepository;
    private TutorialRepository tourRepository;

   
    @Autowired
    public TutorialRatingService(TutorialRatingRepository tutorialRatingRepository, TutorialRepository tourRepository) {
        this.tutorialRatingRepository = tutorialRatingRepository;
        this.tourRepository = tourRepository;
    }
    
    public void createNew(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        tutorialRatingRepository.save(new TutorialRating(verifyTutorial(tourId), customerId,
                score, comment));
    }
    
    public Page<TutorialRating> lookupRatings(int tourId, Pageable pageable) throws NoSuchElementException  {
        return tutorialRatingRepository.findByTutorialId(verifyTutorial(tourId).getId(), pageable);
    }
   
    public TutorialRating update(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        TutorialRating rating = verifyTutorialRating(tourId, customerId);
        rating.setScore(score);
        rating.setComment(comment);
        return tutorialRatingRepository.save(rating);
    }

    
    public TutorialRating updateSome(int tourId, Integer customerId, Integer score, String comment)
            throws NoSuchElementException {
        TutorialRating rating = verifyTutorialRating(tourId, customerId);
        if (score != null) {
            rating.setScore(score);
        }
        if (comment!= null) {
            rating.setComment(comment);
        }
        return tutorialRatingRepository.save(rating);
    }
    
    public void delete(int tourId, Integer customerId) throws NoSuchElementException {
        TutorialRating rating = verifyTutorialRating(tourId, customerId);
        tutorialRatingRepository.delete(rating);
    }
   
    public Double getAverageScore(int tourId)  throws NoSuchElementException  {
        List<TutorialRating> ratings = tutorialRatingRepository.findByTutorialId(verifyTutorial(tourId).getId());
        OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
        return average.isPresent() ? average.getAsDouble():null;
    }
    
    public void rateMany(int tourId,  int score, Integer [] customers) {
        tourRepository.findById(tourId).ifPresent(tour -> {
            for (Integer c : customers) {
                tutorialRatingRepository.save(new TutorialRating(tour, c, score));
            }
        });
    }
   
    private Tutorial verifyTutorial(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tutorial does not exist " + tourId)
        );
    }
    
    private TutorialRating verifyTutorialRating(int tourId, int customerId) throws NoSuchElementException {
        return tutorialRatingRepository.findByTutorialIdAndUserId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tutorial-Rating pair for request("
                        + tourId + " for customer" + customerId));
    }


}
