package by.ermakovich.search_command.controller;

import by.ermakovich.search_command.Exeptions.AuthException;
import by.ermakovich.search_command.Exeptions.InvalidFormsException;
import by.ermakovich.search_command.Exeptions.RegisterException;
import by.ermakovich.search_command.config.jwt.JwtProvider;
import by.ermakovich.search_command.entity.Token;
import by.ermakovich.search_command.entity.Users;
import by.ermakovich.search_command.service.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuttAndRegController {

    @Autowired
    private UsersService userService;
    @Autowired
    private JwtProvider jwtProvider;


    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "registration", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Users user, BindingResult bindingResult)throws InvalidFormsException,RegisterException {
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidFormsException(errorMessage);
        }
        if(!userService.addUser(user)) throw new RegisterException("This login already exists");
        else return new ResponseEntity<String>(HttpStatus.OK);
    }



    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Authorization", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @PostMapping("/auth")
    public ResponseEntity<?> auth(@Valid @RequestBody Users request, BindingResult bindingResult)throws InvalidFormsException, AuthException {

        Users userEntity = userService.findByLoginAndPassword(request.getUsername(), request.getPassword());

        if(userEntity == null) {
            throw new AuthException("Incorrect login or password");
        }

        if(userEntity.getActivationCode() != null) {
            throw new AuthException("Please, go to the email indicated during registration and activate your account");
        }
        else {
            if(bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
                throw new InvalidFormsException(errorMessage);
            }
            String token = jwtProvider.generateToken(userEntity.getUsername());
            return new ResponseEntity<Token>(new Token(token), HttpStatus.OK);
        }


    }




}
