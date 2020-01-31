package ua.epam.service;

import ua.epam.model.Developer;
import ua.epam.repository.jdbc.JdbcDeveloperRepository;

import java.util.Map;

public class DeveloperService {
    private JdbcDeveloperRepository developerRepository = new JdbcDeveloperRepository();

    public Developer create(Developer developer){
        return developerRepository.create(developer);
    }

    public boolean delete(Long id){
        return developerRepository.delete(id);
    }

    public Developer getById(Long id){
        return developerRepository.getById(id);
    }

    public Map<Long, Developer> getAll(){
        return  developerRepository.getAll();
    }
}
