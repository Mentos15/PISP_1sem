package by.ermakovich.search_command.service;


import by.ermakovich.search_command.entity.City;

import by.ermakovich.search_command.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCitys(){
        return cityRepository.findAll();
    }

    public City getCity(String city){
        return cityRepository.findByCity(city);
    }
}
