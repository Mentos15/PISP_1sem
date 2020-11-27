package by.ermakovich.search_command.controller;


import by.ermakovich.search_command.entity.City;
import by.ermakovich.search_command.service.CityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/citys")
public class CityController {

    @Autowired
    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> getAllCitys(){
        return cityService.getAllCitys();
    }

    @GetMapping(value = "/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public  City GetCity(@PathVariable("city") String city){
        return  cityService.getCity(city);
    }
}
