package by.ermakovich.search_command.service;

import by.ermakovich.search_command.entity.Roles;
import by.ermakovich.search_command.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService{

    @Autowired
    private RolesRepository rolesRepository;


    public Roles findByName(String name) {
        return rolesRepository.findByName(name);
    }

    public Roles getById(long id) {
        return rolesRepository.getById(id);
    }
}
