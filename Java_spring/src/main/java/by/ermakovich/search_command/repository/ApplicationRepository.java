package by.ermakovich.search_command.repository;

import by.ermakovich.search_command.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ApplicationRepository extends JpaRepository<Application, Long> {
//    @Query(value = "FROM Application WHERE Application = :name")
//    Application findByName(@Param("name") String name);

    List<Application> findByIdGreaterThan(long id);
    List<Application> findAll();
    Application findById(long id);
}