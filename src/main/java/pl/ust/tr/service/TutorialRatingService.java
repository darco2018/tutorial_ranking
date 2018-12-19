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
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@Transactional
public class TutorialRatingService {
    private TutorialRatingRepository tutorialRatingRepository;
    private TutorialRepository tutorialRepository;

   
    @Autowired
    public TutorialRatingService(TutorialRatingRepository tutorialRatingRepository, TutorialRepository tutorialRepository) {
        this.tutorialRatingRepository = tutorialRatingRepository;
        this.tutorialRepository = tutorialRepository;
    }
    
    public void createNew(int tutorialId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        tutorialRatingRepository.save(new TutorialRating(verifyTutorial(tutorialId), customerId,
                score, comment));
    }

    public Optional<TutorialRating> lookupRatingById(int id){
        return tutorialRatingRepository.findById(id);
    }

    public List<TutorialRating> lookupAll(){
        return tutorialRatingRepository.findAll();
    }
    
    public Page<TutorialRating> lookupRatings(int tutorialId, Pageable pageable) throws NoSuchElementException  {
        return tutorialRatingRepository.findByTutorialId(verifyTutorial(tutorialId).getId(), pageable);
    }
   
    public TutorialRating update(int tutorialId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        TutorialRating rating = verifyTutorialRating(tutorialId, customerId);
        rating.setScore(score);
        rating.setComment(comment);
        return tutorialRatingRepository.save(rating);
    }

    
    public TutorialRating updateSome(int tutorialId, Integer customerId, Integer score, String comment)
            throws NoSuchElementException {
        TutorialRating rating = verifyTutorialRating(tutorialId, customerId);
        if (score != null) {
            rating.setScore(score);
        }
        if (comment!= null) {
            rating.setComment(comment);
        }
        return tutorialRatingRepository.save(rating);
    }
    
    public void delete(int tutorialId, Integer customerId) throws NoSuchElementException {
        TutorialRating rating = verifyTutorialRating(tutorialId, customerId);
        tutorialRatingRepository.delete(rating);
    }
   
    public Double getAverageScore(int tutorialId)  throws NoSuchElementException  {
        List<TutorialRating> ratings = tutorialRatingRepository.findByTutorialId(verifyTutorial(tutorialId).getId());
        OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
        return average.isPresent() ? average.getAsDouble():null;
    }
    
    public void rateMany(int tutorialId,  int score, Integer [] customers) {
        tutorialRepository.findById(tutorialId).ifPresent(tutorial -> {
            for (Integer c : customers) {
                tutorialRatingRepository.save(new TutorialRating(tutorial, c, score));
            }
        });
    }
   
    private Tutorial verifyTutorial(int tutorialId) throws NoSuchElementException {
        return tutorialRepository.findById(tutorialId).orElseThrow(() ->
                new NoSuchElementException("Tutorial does not exist " + tutorialId)
        );
    }
    
    public TutorialRating verifyTutorialRating(int tutorialId, int customerId) throws NoSuchElementException {
        return tutorialRatingRepository.findByTutorialIdAndUserId(tutorialId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tutorial-Rating pair for request("
                        + tutorialId + " for customer" + customerId));
    }


}
