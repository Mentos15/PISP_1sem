package by.ermakovich.search_command.controller;

import by.ermakovich.search_command.config.jwt.JwtProvider;
import by.ermakovich.search_command.entity.Token;
import by.ermakovich.search_command.entity.Users;
import by.ermakovich.search_command.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuttAndRegController {

    @Autowired
    private UsersService userService;
    @Autowired
    private JwtProvider jwtProvider;



    @PostMapping("/register")
    public String registerUser(@RequestBody /*@Valid*/ Users registrationRequest) {
        Users u = new Users();
        u.setPassword(registrationRequest.getPassword());
        u.setUsername(registrationRequest.getUsername());
        userService.saveUser(u);
        return "OK";
    }

    @PostMapping("/auth")
    public Token auth(@RequestBody Users request) {
        Users userEntity = userService.findByLoginAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getUsername());
        return new Token(token);
    }
//    @PostMapping(value = "/registration",produces = MediaType.APPLICATION_JSON_VALUE)
//    public String Register(@RequestBody Users user){
//        if(!usersService.create(user)){
//            return "BAD";
//        }
//        else{
//            return "OK";
//        }
//    }

//    @PostMapping(value = "/auth",produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity Auth(@RequestBody Users user){
//        if(usersService.findByLoginAndPassword(user.getUsername(), user.getPassword()) == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("oshibka");
//        }
//        else{
//            return ResponseEntity.ok("OK");
//        }
//
//    }




}
