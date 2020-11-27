package by.ermakovich.search_command.controller;

import by.ermakovich.search_command.entity.Application;
import by.ermakovich.search_command.entity.Events;
import by.ermakovich.search_command.entity.Token;
import by.ermakovich.search_command.entity.Users;
import by.ermakovich.search_command.service.ApplicationService;
import by.ermakovich.search_command.service.EventsService;
import by.ermakovich.search_command.service.MailSenderService;
import by.ermakovich.search_command.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private EventsService eventsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private MailSenderService mailSenderService;

    public AdminController(ApplicationService applicationService, EventsService eventsService) {
        this.applicationService = applicationService;
        this.eventsService = eventsService;
    }

    @GetMapping(value = "/applications")
    public List<Application> GetAllApplication(){
        return  applicationService.findByIdGreaterThan(0);
    }


    @GetMapping(value = "/applications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Application GetApplicationById(@PathVariable("id") long id){
        return   applicationService.findById(id);
    }


    @DeleteMapping(value = "/applications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity DeleteApplication(@PathVariable("id") long id){
        applicationService.deleteApp(id);
        return ResponseEntity.ok( HttpStatus.OK);
    }

    @DeleteMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity DeleteEvent(@PathVariable("id") long id){

        eventsService.deleteEvent(id);
        return ResponseEntity.ok( HttpStatus.OK);
    }


    @PostMapping(value = "/events/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> AddEvent(@RequestBody Events event){
        eventsService.addEvent(event);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping(value = "/applications/{appId}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity acceptApplication(@PathVariable("appId") long appId, @RequestBody String userName){


        String message = "Привет, ты попал в команду";
        mailSenderService.send(userName, "Ответ на заявку", message);
        applicationService.deleteApp(appId);
        return ResponseEntity.ok( HttpStatus.OK);
    }


    @PostMapping(value = "/applications/{appId}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity cancelApplication(@PathVariable("appId") long appId, @RequestBody String userName){


        String message = "Извините, вы не подходите по требованиям или иным причинам";
        mailSenderService.send(userName, "Ответ на заявку", message);
        applicationService.deleteApp(appId);
        return ResponseEntity.ok( HttpStatus.OK);
    }
}
