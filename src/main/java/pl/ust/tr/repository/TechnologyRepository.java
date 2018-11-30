package pl.ust.tr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import pl.ust.tr.domain.Technology;

//@RepositoryRestResource(collectionResourceRel = "technology-tool", path = "technology-tool")
public interface TechnologyRepository extends CrudRepository<Technology, String> {

    Technology findByName(@Param("name") String name);

    @Override
    @RestResource(exported=false)
    <S extends Technology> S save(S s);

    @Override
    @RestResource(exported=false)
    <S extends Technology> Iterable<S> save(Iterable<S> iterable);

    @Override
    @RestResource(exported=false)
    void delete(String s);

    @Override
    @RestResource(exported=false)
    void delete(Technology technology);

    @Override
    @RestResource(exported=false)
    void delete(Iterable<? extends Technology> iterable);

    @Override
    @RestResource(exported=false)
    void deleteAll();
}
