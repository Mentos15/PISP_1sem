package by.ermakovich.search_command.service;

import by.ermakovich.search_command.entity.Roles;
import by.ermakovich.search_command.entity.Users;
import by.ermakovich.search_command.repository.RolesRepository;
import by.ermakovich.search_command.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public boolean delete(Long id) {
        return false;
    }


//    public Users  findByUsername(String userName){
//        return usersRepository.findByUsername(userName);
//    }

//    public Users findByLoginAndPassword(String login, String password){
//        return usersRepository.findByUsernameAndPassword(login, password);
//    }

//    public boolean create(Users request) {
//        Users user = new Users( );
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        long x = 2;
//        Roles r =  rolesRepository.getById(x);
//        user.getRoles().add(r);
//        usersRepository.save(user);
//        return true;
//    }

    public Users saveUser(Users userEntity) {
        Roles userRole = rolesRepository.findByName("ROLE_USER");
        userEntity.setRoles(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return usersRepository.save(userEntity);
    }

    public Users findByLogin(String login) {
        return usersRepository.findByUsername(login);
    }

    public Users findByLoginAndPassword(String login, String password) {
        Users userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
}
