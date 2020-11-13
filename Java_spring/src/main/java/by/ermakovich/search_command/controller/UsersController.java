package by.ermakovich.search_command.controller;

import by.ermakovich.search_command.entity.Users;
import by.ermakovich.search_command.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public Users GetUser(){
        return usersService.findByLogin("www@mail.ru");
    }



}
