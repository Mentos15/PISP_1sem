package by.ermakovich.search_command.controller;

import by.ermakovich.search_command.entity.Events;
import by.ermakovich.search_command.service.EventsService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventsController {

    @Autowired
    private EventsService eventsService;

    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Get all Events", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @GetMapping
    public List<Events> GetAllEvents(){
        List<Events> allEvents = eventsService.findByIdGreaterThan(0);
        return  allEvents;
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Get all info event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Events GetAllInfoEvent(@PathVariable("id") long id){
        return  eventsService.findById(id);
    }


    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwODY3MDgwMH0.HyWc-_VhyxG64Gj_SlTAAfaPPVQTPv1JPM9MjoH7wvWcs6MITCfQRl_D-9OX-gmkdN5qI-kQ7_zYa8JDTCTRBQ",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Delete event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200") })
    @DeleteMapping(value = "/{id}")
    public void DeleteEvent(long id){
        eventsService.deleteEvent(id);
    }
}
