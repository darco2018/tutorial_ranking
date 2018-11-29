package pl.ust.tr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.ust.tr.domain.Tutorial;

import java.util.List;

//public interface TutorialRepository extends CrudRepository<Tutorial, Integer> {

public interface TutorialRepository extends PagingAndSortingRepository<Tutorial, Integer> {

    //List<Tutorial> findByTechnologyCode(@Param("code") String code);
    Page<Tutorial> findByTechnologyCode(@Param("code") String code, Pageable pageable);
}
