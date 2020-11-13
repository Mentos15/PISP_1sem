package by.ermakovich.search_command.repository;

import by.ermakovich.search_command.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UsersRepository extends JpaRepository<Users, Long> {


    Users findByUsername(/*@Param("username")*/String userName);

//    Users findByUsernameAndPassword(@Param("username")String username, @Param("password")String password);

    Users findByUsernameAndPassword(String username, String password);
}