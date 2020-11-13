package by.ermakovich.search_command.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StepByPage {

    @GetMapping(value = "/register")
    public String getRegPage() {
        return "registration";
    }

    @GetMapping(value = "/auth")
    public String getAuthPage() {
        return "auth";
    }

    @GetMapping(value = "/mainPage")
    public String getMainPage() {
        return "mainPage";
    }
}
