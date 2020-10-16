package by.ermakovich.search_command.repository;

import by.ermakovich.search_command.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EventsRepository extends JpaRepository<Events, Long> {
    @Query(value = "FROM Events WHERE title = :title")
    List<Events> findByTitle(@Param("title") String title);
}