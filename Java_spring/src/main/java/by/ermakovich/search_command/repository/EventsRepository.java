package by.ermakovich.search_command.repository;

import by.ermakovich.search_command.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {


    List<Events> findByIdGreaterThan(long id);

    List<Events> findByTitle(String title);

    Events findById(long id);


}