package by.ermakovich.search_command.repository;

import by.ermakovich.search_command.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "FROM Users WHERE username = :username")
    Users findByUsername(@Param("username")String username);


    @Query(value = "FROM Users WHERE username = :username AND password = :password")
    Users findByUsernameAndPassword(@Param("username")String username, @Param("password")String password);
}