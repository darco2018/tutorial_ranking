package pl.ust.tr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ust.tr.domain.Level;
import pl.ust.tr.domain.Skill;
import pl.ust.tr.domain.Tutorial;
import pl.ust.tr.domain.Type;
import pl.ust.tr.repository.SkillRepository;
import pl.ust.tr.repository.TutorialRepository;

@Service
public class TutorialService {

    private TutorialRepository tutorialRepository;
    private SkillRepository skillRepository;

    @Autowired
    public TutorialService(TutorialRepository tutorialRepository, SkillRepository skillRepository) {
        this.tutorialRepository = tutorialRepository;
        this.skillRepository = skillRepository;
    }

    public Tutorial createTutorial(String title, String link, String description, int price, String duration,
                                   String keywords, String techologyName, Type type, Level level) {
        Skill skill = this.skillRepository.findByName(techologyName);
        if (skill == null) {
            throw new RuntimeException("Skill does not exist: " + techologyName);
        }

        return tutorialRepository.save( new Tutorial ( title,  link,  description,  price,  duration, keywords, skill, type, level));

    }

    public Iterable<Tutorial> lookup(){
        return this.tutorialRepository.findAll();
    }

    public long total(){
        return this.tutorialRepository.count();
    }

}
