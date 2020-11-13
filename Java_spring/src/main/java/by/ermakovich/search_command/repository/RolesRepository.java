package by.ermakovich.search_command.repository;

import by.ermakovich.search_command.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles, Long> {


    Roles findByName(String name);
    Roles getById(long id);
}