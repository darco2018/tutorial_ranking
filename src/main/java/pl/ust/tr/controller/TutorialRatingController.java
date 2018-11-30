package pl.ust.tr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ust.tr.domain.Tutorial;
import pl.ust.tr.domain.TutorialRating;
import pl.ust.tr.domain.TutorialRatingPk;
import pl.ust.tr.repository.TutorialRatingRepository;
import pl.ust.tr.repository.TutorialRepository;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/tutorial/{tutorialId}/ratings")
public class TutorialRatingController {

    private TutorialRatingRepository tutorialRatingRepository;
    private TutorialRepository tutorialRepository;

    @Autowired
    public TutorialRatingController(TutorialRatingRepository tutorialRatingRepository, TutorialRepository tutorialRepository) {
        this.tutorialRatingRepository = tutorialRatingRepository;
        this.tutorialRepository = tutorialRepository;
    }

    protected TutorialRatingController(){}

    private RatingDto toDto(TutorialRating tutorialRating){
        return new RatingDto(tutorialRating.getScore(), tutorialRating.getComment(), tutorialRating.getPk().getUserId());
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTutorialRating(@PathVariable(value = "tutorialId") int tutorialID,
                                     @RequestBody @Validated RatingDto ratingDto){

        Tutorial tutorial = verifyTutorial(tutorialID);
        tutorialRatingRepository.save(new TutorialRating(new TutorialRatingPk(tutorial, ratingDto.getUserId()),
                                        ratingDto.getScore(), ratingDto.getComment()));

    }



    ////////////////////////////////// HELPERS ////////////////////////////////
    private Tutorial verifyTutorial(int tutorialId) throws NoSuchElementException {
        Tutorial tutorial = tutorialRepository.findOne(tutorialId);
        if(tutorial == null)
            throw new NoSuchElementException("No such tutorial in the database: " + tutorialId);

        return tutorial;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex){
        return ex.getMessage();
    }
}
