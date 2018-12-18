package pl.ust.tr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ust.tr.domain.Tutorial;
import pl.ust.tr.domain.TutorialRating;
import pl.ust.tr.repository.TutorialRatingRepository;
import pl.ust.tr.repository.TutorialRepository;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/tutorials/{tutorialId}/ratings")
public class TutorialRatingController {

    private TutorialRatingRepository tutorialRatingRepository;
    private TutorialRepository tutorialRepository;

    @Autowired
    public TutorialRatingController(TutorialRatingRepository tutorialRatingRepository, TutorialRepository tutorialRepository) {
        this.tutorialRatingRepository = tutorialRatingRepository;
        this.tutorialRepository = tutorialRepository;
    }

    protected TutorialRatingController(){}

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTutorialRating(@PathVariable(value = "tutorialId") int tutorialId,
                                     @RequestBody @Validated RatingDto ratingDto){

        Tutorial tutorial = verifyTutorial(tutorialId);
        tutorialRatingRepository.save(
                new TutorialRating(tutorial, ratingDto.getUserId(),ratingDto.getScore(), ratingDto.getComment()));

    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<RatingDto> getAllRatingsForTutorialPageable(@PathVariable(value = "tutorialId") int tutorialId,
                                                            Pageable pageable){

        verifyTutorial(tutorialId);
        Page<TutorialRating> page = tutorialRatingRepository.findByTutorialId(tutorialId, pageable);
        List<RatingDto> ratingDtoList = page.getContent()
                                              .stream()
                                              .map(tutorialRating -> toDto(tutorialRating))
                                              .collect(Collectors.toList());

        return new PageImpl<RatingDto>(ratingDtoList, pageable, page.getTotalPages());

    }

    /*@RequestMapping(method = RequestMethod.GET, path = "/average")
    public String getTutorialAverageRating(@PathVariable(value = "tutorialId") int tutorialId){

        List<TutorialRating> ratings = getRatings(tutorialId);

        double sum = 0.0;
        //ratings.forEach(rating -> sum += rating.getScore());
        for (TutorialRating rating : ratings) {
            sum += rating.getScore();
        }
        return "average: " +  sum / (double)ratings.size();
    }*/

    @RequestMapping(method = RequestMethod.GET, path = "/average")
    public AbstractMap.SimpleEntry<String, Double> getAverageRating(@PathVariable(value = "tutorialId") int tutorialId){
        List<TutorialRating> ratings = getRatings(tutorialId);
        OptionalDouble average = ratings
                .stream()
                .mapToDouble(TutorialRating::getScore)
                .average();
        return new AbstractMap.SimpleEntry<>("average", average.isPresent() ? average.getAsDouble() : null);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RatingDto updateWithPut(@PathVariable (value = "tutorialId") int tutorialId,
                                   @RequestBody @Validated RatingDto ratingDto){

        TutorialRating tutorialRating = verifyTutorialRating(tutorialId, ratingDto.getUserId());
        tutorialRating.setScore(ratingDto.getScore());
        tutorialRating.setComment(ratingDto.getComment());
        return toDto(tutorialRatingRepository.save(tutorialRating));

    }

    @RequestMapping(method = RequestMethod.PATCH)
    public RatingDto updateWithPatch(@PathVariable (value = "tutorialId") int tutorialId,
                                   @RequestBody @Validated RatingDto ratingDto){

        TutorialRating tutorialRating = verifyTutorialRating(tutorialId, ratingDto.getUserId());
        if(ratingDto.getScore() != null){
            tutorialRating.setScore(ratingDto.getScore());
        }

        if(ratingDto.getComment() != null){
            tutorialRating.setComment(ratingDto.getComment());
        }

        return toDto(tutorialRatingRepository.save(tutorialRating));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{userId}")
    public void delete(@PathVariable (value = "tutorialId") int tutorialId,
                       @PathVariable (value = "userId") int userId){
        TutorialRating tutorialRating = verifyTutorialRating(tutorialId, userId);
        tutorialRatingRepository.delete(tutorialRating);
    }

    ////////////////////////////////// HELPERS ////////////////////////////////
    private List<TutorialRating> getRatings(int tutorialId){
        verifyTutorial(tutorialId);
        return tutorialRatingRepository.findByTutorialId(tutorialId);
    }

    private TutorialRating verifyTutorialRating(int tutorialId, int userId) throws NoSuchElementException {
        return tutorialRatingRepository.findByTutorialIdAndUserId(tutorialId, userId).orElseThrow(() ->
            new NoSuchElementException("There's no rating for tutorial " + tutorialId + " userId: " + userId));
    }



    private Tutorial verifyTutorial(int tutorialId) throws NoSuchElementException {
        return tutorialRepository.findById(tutorialId).orElseThrow(() ->
            new NoSuchElementException("No such tutorial in the database: " + tutorialId));

    }

    private RatingDto toDto(TutorialRating tutorialRating){
        return new RatingDto(tutorialRating.getScore(), tutorialRating.getComment(), tutorialRating.getUserId());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex){

        return ex.getMessage();
    }
}
