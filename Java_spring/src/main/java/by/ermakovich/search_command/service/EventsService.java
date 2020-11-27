package by.ermakovich.search_command.service;

import by.ermakovich.search_command.entity.Events;
import by.ermakovich.search_command.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventsService {

    @Autowired
    private EventsRepository eventsRepository;

    public Events addEvent(Events event){
        return eventsRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventsRepository.deleteById(id);
    }

    public List<Events> findByIdGreaterThan(long id) {
        return eventsRepository.findByIdGreaterThan(0);
    }

    public List<Events> findByTitle(String title) {
        return eventsRepository.findByTitle(title);
    }

    public Events findById(long id){return eventsRepository.findById(id);}


}
