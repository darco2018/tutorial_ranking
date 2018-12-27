package pl.ust.tr.rating;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/tutorials/{tutorialId}/ratings")
public class RateSingleTutorialController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateSingleTutorialController.class);

    private RatingService ratingService;
    private RatingAssembler ratingAssembler;

    @Autowired
    public RateSingleTutorialController(RatingService ratingService, RatingAssembler ratingAssembler) {
        this.ratingService = ratingService;
        this.ratingAssembler = ratingAssembler;
    }

    protected RateSingleTutorialController(){}

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTutorialRating(@PathVariable(value = "tutorialId") int tutorialId,
                                     @RequestBody @Validated RatingDto ratingDto){
        LOGGER.info("POST /tours/{}/ratings", tutorialId);
        ratingService.createNew(tutorialId, ratingDto.getUserId(),ratingDto.getScore(), ratingDto.getComment());

    }

    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<RatingDto> getAllRatingsForTutorialPageable(@PathVariable(value = "tutorialId") int tutorialId,
                                                                                Pageable pageable,
                                                                                PagedResourcesAssembler pagedAssembler){
                                                                                // generate HATEOUS links for previous, next, last, etc.
        LOGGER.info("GET /tours/{}/ratings", tutorialId);
        Page<Rating> page = ratingService.lookupRatings(tutorialId, pageable);
        return pagedAssembler.toResource(page, ratingAssembler);

        /*Page<Rating> page = ratingService.lookupRatingById(tutorialId, pageable);
        List<RatingDto> ratingDtoList = page.getContent()
                                              .stream()
                                              .map(tutorialRating -> toDto(tutorialRating))
                                              .collect(Collectors.toList());

        return new PageImpl<RatingDto>(ratingDtoList, pageable, page.getTotalPages());*/
    }


    @RequestMapping(method = RequestMethod.GET, path = "/average")
    public AbstractMap.SimpleEntry<String, Double> getAverageRating(@PathVariable(value = "tutorialId") int tutorialId){

        LOGGER.info("GET /tours/{}/ratings/average", tutorialId);
        return new AbstractMap.SimpleEntry<>("average", ratingService.getAverageScore(tutorialId));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RatingDto updateWithPut(@PathVariable (value = "tutorialId") int tutorialId,
                                   @RequestBody @Validated RatingDto ratingDto){
        LOGGER.info("PUT /tours/{}/ratings", tutorialId);
        return toDto(ratingService.update(tutorialId, ratingDto.getUserId(), ratingDto.getScore(),
                ratingDto.getComment()));

    }

    @RequestMapping(method = RequestMethod.PATCH)
    public RatingDto updateWithPatch(@PathVariable (value = "tutorialId") int tutorialId,
                                   @RequestBody @Validated RatingDto ratingDto){
        LOGGER.info("PATCH /tours/{}/ratings", tutorialId);
        return toDto(ratingService.updateSome(tutorialId, ratingDto.getUserId(), ratingDto.getScore(),
                ratingDto.getComment()));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{userId}")
    public void delete(@PathVariable (value = "tutorialId") int tutorialId,
                       @PathVariable (value = "userId") int userId){
        LOGGER.info("DELETE /tours/{}/ratings/{}", tutorialId, userId);
        ratingService.delete(tutorialId, userId);
    }

    ////////////////////////////////// HELPERS ////////////////////////////////

    private RatingDto toDto(Rating rating){
        return new RatingDto(rating.getScore(), rating.getComment(), rating.getUserId());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex){

        return ex.getMessage();
    }
}
