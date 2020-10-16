package by.ermakovich.search_command.repository;

import by.ermakovich.search_command.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query(value = "FROM Roles WHERE name = :name")
    List<Roles> findByName(@Param("name") String name);
}