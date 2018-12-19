package pl.ust.tr.controller;

import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ust.tr.domain.TutorialRating;
import pl.ust.tr.repository.TutorialRepository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Rating Assembler, convert TutorialRating to a Hateoas-Supported Rating class
 */
@Component
public class RatingAssembler extends ResourceAssemblerSupport<TutorialRating,RatingDto> {

    //Helper to fetch Spring Data Rest Repository links.
    private RepositoryEntityLinks entityLinks;

    public RatingAssembler( RepositoryEntityLinks entityLinks) {
        super(RatingController.class, RatingDto.class);
        this.entityLinks = entityLinks;
    }
    
    @Override
    public RatingDto toResource(TutorialRating tutorialRating) {
        RatingDto rating = new RatingDto(tutorialRating.getScore(), tutorialRating.getComment(), tutorialRating.getUserId());

        // "self" : ".../ratings/{ratingId}"
        ControllerLinkBuilder ratingLink = linkTo(methodOn(RatingController.class).getRating(tutorialRating.getId()));
        rating.add(ratingLink.withSelfRel());

        //"tutorial" : ".../tutorials/{tutorialId}"
       Link tutorialLink = entityLinks.linkToSingleResource(TutorialRepository.class, tutorialRating.getTutorial().getId());
       rating.add(tutorialLink.withRel("tutorial"));
       return rating;
    }

}
