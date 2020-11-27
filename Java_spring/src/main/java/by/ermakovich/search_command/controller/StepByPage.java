package by.ermakovich.search_command.controller;

import by.ermakovich.search_command.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StepByPage {


    @Autowired
    private UsersService usersService;

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

    @GetMapping(value = "/adminPage")
    public String getAdminPage() {
        return "adminPage";
    }


    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = usersService.acrivateUser(code);

        if(isActivated) {
            model.addAttribute("message", "User successfully activated");
        }
        else {
            model.addAttribute("message", "Activation code is not found");
        }
        return "auth";
    }
}
