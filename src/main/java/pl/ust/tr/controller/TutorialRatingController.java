package pl.ust.tr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ust.tr.domain.Tutorial;
import pl.ust.tr.domain.TutorialRating;
import pl.ust.tr.repository.TutorialRatingRepository;
import pl.ust.tr.repository.TutorialRepository;
import pl.ust.tr.service.TutorialRatingService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/tutorials/{tutorialId}/ratings")
public class TutorialRatingController {

    private TutorialRatingService tutorialRatingService;
    private RatingAssembler ratingAssembler;

    @Autowired
    public TutorialRatingController(TutorialRatingService tutorialRatingService, RatingAssembler ratingAssembler) {
        this.tutorialRatingService = tutorialRatingService;
        this.ratingAssembler = ratingAssembler;
    }

    protected TutorialRatingController(){}

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTutorialRating(@PathVariable(value = "tutorialId") int tutorialId,
                                     @RequestBody @Validated RatingDto ratingDto){

        tutorialRatingService.createNew(tutorialId, ratingDto.getUserId(),ratingDto.getScore(), ratingDto.getComment());

    }

    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<RatingDto> getAllRatingsForTutorialPageable(@PathVariable(value = "tutorialId") int tutorialId,
                                                                                Pageable pageable,
                                                                                PagedResourcesAssembler pagedAssembler){
                                                                                // generate HATEOUS links for previous, next, last, etc.
        Page<TutorialRating> page = tutorialRatingService.lookupRatings(tutorialId, pageable);
        return pagedAssembler.toResource(page, ratingAssembler);

        /*Page<TutorialRating> page = tutorialRatingService.lookupRatingById(tutorialId, pageable);
        List<RatingDto> ratingDtoList = page.getContent()
                                              .stream()
                                              .map(tutorialRating -> toDto(tutorialRating))
                                              .collect(Collectors.toList());

        return new PageImpl<RatingDto>(ratingDtoList, pageable, page.getTotalPages());*/
    }


    @RequestMapping(method = RequestMethod.GET, path = "/average")
    public AbstractMap.SimpleEntry<String, Double> getAverageRating(@PathVariable(value = "tutorialId") int tutorialId){
        return new AbstractMap.SimpleEntry<>("average", tutorialRatingService.getAverageScore(tutorialId));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RatingDto updateWithPut(@PathVariable (value = "tutorialId") int tutorialId,
                                   @RequestBody @Validated RatingDto ratingDto){

        return toDto(tutorialRatingService.update(tutorialId, ratingDto.getUserId(), ratingDto.getScore(),
                ratingDto.getComment()));

    }

    @RequestMapping(method = RequestMethod.PATCH)
    public RatingDto updateWithPatch(@PathVariable (value = "tutorialId") int tutorialId,
                                   @RequestBody @Validated RatingDto ratingDto){

        return toDto(tutorialRatingService.updateSome(tutorialId, ratingDto.getUserId(), ratingDto.getScore(),
                ratingDto.getComment()));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{userId}")
    public void delete(@PathVariable (value = "tutorialId") int tutorialId,
                       @PathVariable (value = "userId") int userId){

        tutorialRatingService.delete(tutorialId, userId);
    }

    ////////////////////////////////// HELPERS ////////////////////////////////

    private RatingDto toDto(TutorialRating tutorialRating){
        return new RatingDto(tutorialRating.getScore(), tutorialRating.getComment(), tutorialRating.getUserId());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex){

        return ex.getMessage();
    }
}
