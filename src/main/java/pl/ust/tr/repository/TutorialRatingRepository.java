package pl.ust.tr.repository;

import org.hibernate.boot.model.source.spi.JpaCallbackSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.ust.tr.domain.TutorialRating;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TutorialRatingRepository extends JpaRepository<TutorialRating, Integer> {

    List<TutorialRating> findByTutorialId(Integer tutorialId);
    Page<TutorialRating> findByTutorialId(Integer tutorialId, Pageable pageable);

    //TutorialRating findByTutorialIdAndUserId(Integer tutorialId, Integer userId);
    Optional<TutorialRating> findByTutorialIdAndUserId(Integer tutorialId, Integer userId);

}
