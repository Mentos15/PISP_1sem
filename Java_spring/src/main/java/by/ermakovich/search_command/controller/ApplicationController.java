package by.ermakovich.search_command.controller;


import by.ermakovich.search_command.entity.Application;
import by.ermakovich.search_command.entity.Events;
import by.ermakovich.search_command.service.ApplicationService;
import by.ermakovich.search_command.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UsersService usersService;


    @PostMapping
    public Application AddApplication(@RequestBody Application app){
//        int userId = usersService.findByLogin();
        return  applicationService.addApp(app);
    }



}
