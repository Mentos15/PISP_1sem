package by.ermakovich.search_command.repository;

import by.ermakovich.search_command.entity.Application;
import by.ermakovich.search_command.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAll();


    @Query("select b from City b where b.city = :city")
    City findByCity(@Param("city") String city);
}
