package pl.ust.tr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.ust.tr.domain.Technology;

public interface TechnologyRepository extends CrudRepository<Technology, String> {

    Technology findByName(@Param("name") String name);
}
