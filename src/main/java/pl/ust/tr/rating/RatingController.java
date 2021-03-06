package pl.ust.tr.rating;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;

@Api(description = "API just tu pull ratings")
@RestController
@RequestMapping(path = "/ratings")
public class RatingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);

    private RatingService ratingService;
    private RatingAssembler assembler;

    @Autowired
    public RatingController(RatingService ratingService, RatingAssembler assembler) {
        this.ratingService = ratingService;
        this.assembler = assembler;
    }

    @GetMapping
    @ApiOperation(value = "Find all ratings")
    @ApiResponses(@ApiResponse(code = 200, message = "OK"))
    public List<RatingDto> getAll() {
        LOGGER.info("GET /ratings");
        return assembler.toResources(ratingService.lookupAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find ratings by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Rating not found") })
    public RatingDto getRating( @ApiParam(value = "rating identifier")
                                @PathVariable("id") Integer id) {

        LOGGER.info("GET /ratings/{id}", id);
        return assembler.toResource(ratingService.lookupRatingById(id)
                .orElseThrow(() -> new NoSuchElementException("Rating " + id + " not found"))
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        LOGGER.error("Unable to complete transaction", ex);
        return ex.getMessage();
    }
}
