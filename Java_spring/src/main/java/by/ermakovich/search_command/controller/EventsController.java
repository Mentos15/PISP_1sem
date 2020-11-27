package by.ermakovich.search_command.controller;

import by.ermakovich.search_command.entity.Events;
import by.ermakovich.search_command.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventsController {

    @Autowired
    private EventsService eventsService;

    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping
    public List<Events> GetAllEvents(){
        List<Events> allEvents = eventsService.findByIdGreaterThan(0);
        return  allEvents;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Events GetAllInfoEvent(@PathVariable("id") long id){
        return  eventsService.findById(id);
    }



    @DeleteMapping(value = "/{id}")
    public void DeleteEvent(long id){
        eventsService.deleteEvent(id);
    }
}
