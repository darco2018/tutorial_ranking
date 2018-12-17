package pl.ust.tr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.ust.tr.domain.TutorialRating;
import pl.ust.tr.domain.TutorialRatingPk;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface TutorialRatingRepository extends CrudRepository<TutorialRating, TutorialRatingPk> {

    List<TutorialRating> findByPkTutorialId(Integer tutorialId);
    Page<TutorialRating> findByPkTutorialId(Integer tutorialId, Pageable pageable);

    TutorialRating findByPkTutorialIdAndPkUserId(Integer tutorialId, Integer userId);

}
