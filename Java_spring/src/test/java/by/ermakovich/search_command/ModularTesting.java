package by.ermakovich.search_command;

import by.ermakovich.search_command.entity.Roles;
import by.ermakovich.search_command.entity.Users;
import by.ermakovich.search_command.repository.UsersRepository;
import by.ermakovich.search_command.service.MailSenderService;
import by.ermakovich.search_command.service.RolesService;
import by.ermakovich.search_command.service.UsersService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModularTesting {

    @Autowired
    private UsersService usersService;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private RolesService rolesService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MailSenderService mailSenderService;


    @Test
    public void addUser() {
        Users user = new Users();

        user.setUsername("some@mail.ru");

        boolean isUserCreated = usersService.addUser(user);

        Assert.assertTrue(isUserCreated);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(rolesService.findByName("ROLE_USER")));

        Mockito.verify(usersRepository, Mockito.times(1)).save(user);
        Mockito.verify(mailSenderService, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getUsername()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    public void addUserFail() {
        Users user = new Users();

        user.setUsername("vitaly");

        Mockito.doReturn(new Users())
                .when(usersRepository)
                .findByUsername("vitaly");

        boolean isUserCreated = usersService.addUser(user);

        Assert.assertFalse(isUserCreated);

        Mockito.verify(usersRepository, Mockito.times(0)).save(ArgumentMatchers.any(Users.class));
        Mockito.verify(mailSenderService, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    public void activateUser() {
        Users user = new Users();

        Mockito.doReturn(user)
                .when(usersRepository)
                .findByActivationCode("activate");

        boolean isUserActivated = usersService.acrivateUser("activate");

        Assert.assertTrue(isUserActivated);
        Assert.assertNull(user.getActivationCode());

        Mockito.verify(usersRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void activateUserFail() {
        boolean isUserActivated = usersService.acrivateUser("activate me");

        Assert.assertFalse(isUserActivated);
        Mockito.verify(usersRepository, Mockito.times(0)).save(ArgumentMatchers.any(Users.class));
    }
}
