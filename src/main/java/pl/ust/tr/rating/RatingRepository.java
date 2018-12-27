package pl.ust.tr.rating;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.ust.tr.rating.Rating;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    List<Rating> findByTutorialId(Integer tutorialId);
    Page<Rating> findByTutorialId(Integer tutorialId, Pageable pageable);

    //Rating findByTutorialIdAndUserId(Integer tutorialId, Integer userId);
    Optional<Rating> findByTutorialIdAndUserId(Integer tutorialId, Integer userId);

}
