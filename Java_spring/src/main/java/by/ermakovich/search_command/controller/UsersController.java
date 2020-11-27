package by.ermakovich.search_command.controller;

import by.ermakovich.search_command.entity.Users;
import by.ermakovich.search_command.service.EventsService;
import by.ermakovich.search_command.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Users GetUser(@PathVariable("username") String  username){
        return usersService.findByLogin(username);
    }



}
