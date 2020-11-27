package by.ermakovich.search_command.service;

import by.ermakovich.search_command.entity.Roles;
import by.ermakovich.search_command.entity.Users;
import by.ermakovich.search_command.repository.RolesRepository;
import by.ermakovich.search_command.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;


@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSenderService mailSenderService;

    public boolean delete(Long id) {
        return false;
    }




    public boolean acrivateUser(String code) {
        Users user = usersRepository.findByActivationCode(code);

        if(user == null){
            return false;
        }

        user.setActivationCode(null);
        usersRepository.save(user);
        return true;
    }



//    public Users saveUser(Users userEntity) {
//        Roles userRole = rolesRepository.findByName("ROLE_USER");
//        userEntity.setRoles(userRole);
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
//        userEntity.setActivate(0);
//        return usersRepository.save(userEntity);
//    }

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


    public boolean addUser(Users user) {
        if(findByLogin(user.getUsername()) != null) {
            return false;
        }
        else {
            Roles userRole = rolesRepository.findByName("ROLE_USER");
            user.setRoles(userRole);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationCode(UUID.randomUUID().toString());

            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Search command application. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getUsername(), "Activation code", message);

            usersRepository.save(user);
            return true;
        }
    }
}
