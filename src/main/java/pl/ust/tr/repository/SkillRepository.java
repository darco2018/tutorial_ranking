package pl.ust.tr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.ust.tr.domain.Skill;

public interface SkillRepository extends CrudRepository<Skill, String> {

    Skill findByName(@Param("name") String name);
}
