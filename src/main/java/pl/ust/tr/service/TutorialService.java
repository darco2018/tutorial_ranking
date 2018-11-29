package pl.ust.tr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ust.tr.domain.Level;
import pl.ust.tr.domain.Technology;
import pl.ust.tr.domain.Tutorial;
import pl.ust.tr.domain.Type;
import pl.ust.tr.repository.TechnologyRepository;
import pl.ust.tr.repository.TutorialRepository;

@Service
public class TutorialService {

    private TutorialRepository tutorialRepository;
    private TechnologyRepository technologyRepository;

    @Autowired
    public TutorialService(TutorialRepository tutorialRepository, TechnologyRepository technologyRepository) {
        this.tutorialRepository = tutorialRepository;
        this.technologyRepository = technologyRepository;
    }

    public Tutorial createTutorial(String title, String link, String description, int price, String duration,
                                   String keywords, String techologyName, Type type, Level level) {
        Technology technology = this.technologyRepository.findByName(techologyName);
        if (technology == null) {
            throw new RuntimeException("Technology does not exist: " + techologyName);
        }

        return tutorialRepository.save( new Tutorial ( title,  link,  description,  price,  duration, keywords, technology, type, level));

    }

    public Iterable<Tutorial> lookup(){
        return this.tutorialRepository.findAll();
    }

    public long total(){
        return this.tutorialRepository.count();
    }

}
