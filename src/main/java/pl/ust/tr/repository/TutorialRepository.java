package pl.ust.tr.repository;

import org.springframework.data.repository.CrudRepository;
import pl.ust.tr.domain.Tutorial;

public interface TutorialRepository extends CrudRepository<Tutorial, Integer> {
}
