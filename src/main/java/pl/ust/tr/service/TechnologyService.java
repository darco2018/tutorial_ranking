package pl.ust.tr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ust.tr.domain.Technology;
import pl.ust.tr.repository.TechnologyRepository;

@Service
public class TechnologyService {

    private TechnologyRepository technologyRepository;

    @Autowired
    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public Technology createTechnology(String code, String name){
        if(!technologyRepository.exists(code)){
            technologyRepository.save(new Technology(code, name));
        }

        return null;
    }

    public Iterable<Technology> lookup(){
        return technologyRepository.findAll();
    }

    public long count(){
        return technologyRepository.count();
    }
}


