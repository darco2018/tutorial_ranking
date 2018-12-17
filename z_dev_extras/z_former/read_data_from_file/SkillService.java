package pl.ust.tr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ust.tr.domain.Skill;
import pl.ust.tr.repository.SkillRepository;

@Service
public class SkillService {

    private SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkill(String code, String name){
        if(!skillRepository.existsById(code)){
            skillRepository.save(new Skill(code, name));
        }

        return null;
    }

    public Iterable<Skill> lookup(){
        return skillRepository.findAll();
    }

    public long total(){
        return skillRepository.count();
    }
}


