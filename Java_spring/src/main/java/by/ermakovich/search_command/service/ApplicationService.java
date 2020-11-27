package by.ermakovich.search_command.service;


import by.ermakovich.search_command.entity.Application;
import by.ermakovich.search_command.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application addApp(Application application){
        return applicationRepository.save(application);
    }

    public void deleteApp(Long id) {
        applicationRepository.deleteById(id);
    }
    public List<Application> findByIdGreaterThan(long id) {
        return applicationRepository.findByIdGreaterThan(0);
    }

    public Application findById(long id) {
        return applicationRepository.findById(id);
    }

}
