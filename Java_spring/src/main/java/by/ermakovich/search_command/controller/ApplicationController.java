package by.ermakovich.search_command.controller;


import by.ermakovich.search_command.Exeptions.InvalidFormsException;
import by.ermakovich.search_command.entity.Application;
import by.ermakovich.search_command.entity.Events;
import by.ermakovich.search_command.service.ApplicationService;
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
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UsersService usersService;


    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Add application", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @PostMapping
    public Application AddApplication(@Valid @RequestBody Application app, BindingResult bindingResult)throws InvalidFormsException{
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidFormsException(errorMessage);
        }
        return  applicationService.addApp(app);
    }



}
