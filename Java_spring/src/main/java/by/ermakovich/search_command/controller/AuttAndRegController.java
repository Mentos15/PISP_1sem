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
    public ResponseEntity<?> registerUser( @RequestBody Users user) {
//        if(bindingResult.hasErrors()) {
//            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
//            throw new InvalidFormsException(errorMessage);
//        }
        if(!userService.addUser(user))  return new ResponseEntity<String>("BAD", HttpStatus.NOT_FOUND); /*throw new RegisterException("This login already exists");*/
        else return new ResponseEntity<String>("OK", HttpStatus.OK);
    }


//    public String registerUser(@RequestBody /*@Valid*/ Users registrationRequest) {
//        Users u = new Users();
//        u.setPassword(registrationRequest.getPassword());
//        u.setUsername(registrationRequest.getUsername());
//        userService.saveUser(u);
//        return "OK";
//    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody Users request) {

        Users userEntity = userService.findByLoginAndPassword(request.getUsername(), request.getPassword());
        if(userEntity.getActivationCode() != null) {
            //throw new AuthException("Please, go to the email indicated during registration and activate your account");
            String token = jwtProvider.generateToken(userEntity.getUsername());
            return new ResponseEntity<>(new Token(token), HttpStatus.NOT_FOUND);
        }
        else {
            String token = jwtProvider.generateToken(userEntity.getUsername());
            return new ResponseEntity<Token>(new Token(token), HttpStatus.OK);
        }


    }




}
