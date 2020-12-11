package by.ermakovich.search_command.controller;

import by.ermakovich.search_command.Exeptions.InvalidFormsException;
import by.ermakovich.search_command.entity.Application;
import by.ermakovich.search_command.entity.Events;
import by.ermakovich.search_command.entity.Token;
import by.ermakovich.search_command.entity.Users;
import by.ermakovich.search_command.service.ApplicationService;
import by.ermakovich.search_command.service.EventsService;
import by.ermakovich.search_command.service.MailSenderService;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Tag(name = "Admin controller")
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


    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "get all applications", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @GetMapping(value = "/applications")
    public List<Application> GetAllApplication(){
        return  applicationService.findByIdGreaterThan(0);
    }


    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "get applications by Id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @GetMapping(value = "/applications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Application GetApplicationById(@PathVariable("id") long id){
        return   applicationService.findById(id);
    }


    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Delete application", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @DeleteMapping(value = "/applications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity DeleteApplication(@PathVariable("id") long id){
        applicationService.deleteApp(id);
        return ResponseEntity.ok( HttpStatus.OK);
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Delete event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @DeleteMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity DeleteEvent(@PathVariable("id") long id){

        eventsService.deleteEvent(id);
        return ResponseEntity.ok( HttpStatus.OK);
    }


    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Add event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @PostMapping(value = "/events/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> AddEvent(@Valid @RequestBody Events event, BindingResult bindingResult) throws InvalidFormsException {

        if(bindingResult.hasErrors()) {

            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidFormsException(errorMessage);
        }
        eventsService.addEvent(event);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Accept application", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @PostMapping(value = "/applications/{appId}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity acceptApplication(@PathVariable("appId") long appId, @RequestBody String userName){


        String message = "Привет, ты попал в команду";
        mailSenderService.send(userName, "Ответ на заявку", message);
        applicationService.deleteApp(appId);
        return ResponseEntity.ok( HttpStatus.OK);
    }


    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Cancel application", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @PostMapping(value = "/applications/{appId}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity cancelApplication(@PathVariable("appId") long appId, @RequestBody String userName){


        String message = "Извините, вы не подходите по требованиям или иным причинам";
        mailSenderService.send(userName, "Ответ на заявку", message);
        applicationService.deleteApp(appId);
        return ResponseEntity.ok( HttpStatus.OK);
    }
}
