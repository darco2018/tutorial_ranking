package pl.ust.tr.skill;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.ust.tr.skill.Skill;

import java.util.Optional;

public interface SkillRepository extends CrudRepository<Skill, String> {

    Optional<Skill> findByName(@Param("name") String name);

}
