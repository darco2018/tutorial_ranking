package pl.ust.tr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.ust.tr.domain.Skill;

import java.util.Optional;

public interface SkillRepository extends CrudRepository<Skill, String> {

    Optional<Skill> findByName(@Param("name") String name);
}
