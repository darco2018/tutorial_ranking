package pl.ust.tr.repository;

import org.springframework.data.repository.CrudRepository;
import pl.ust.tr.domain.Technology;

public interface TechnologyRepository extends CrudRepository<Technology, String> {
}
