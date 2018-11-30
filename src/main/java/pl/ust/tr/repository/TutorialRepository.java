package pl.ust.tr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import pl.ust.tr.domain.Tutorial;

import java.util.List;

//public interface TutorialRepository extends CrudRepository<Tutorial, Integer> {

public interface TutorialRepository extends PagingAndSortingRepository<Tutorial, Integer> {

    //List<Tutorial> findBySkillCode(@Param("code") String code);
    Page<Tutorial> findBySkillCode(@Param("code") String code, Pageable pageable);

    @Override
    @RestResource(exported=false)
    <S extends Tutorial> S save(S s);

    @Override
    @RestResource(exported=false)
    <S extends Tutorial> Iterable<S> save(Iterable<S> iterable);

    @Override
    @RestResource(exported=false)
    void delete(Integer integer);

    @Override
    @RestResource(exported=false)
    void delete(Tutorial tutorial);

    @Override
    @RestResource(exported=false)
    void delete(Iterable<? extends Tutorial> iterable);

    @Override
    @RestResource(exported=false)
    void deleteAll();

}
