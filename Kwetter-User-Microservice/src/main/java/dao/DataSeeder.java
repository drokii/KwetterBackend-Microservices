package dao;

import exceptions.MessageTooLongException;
import exceptions.UserNotFoundException;
import models.Role;
import models.User;
import service.UserService;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;


@Singleton
@Startup
public class DataSeeder {

    @Inject
    private UserService userService;

    @Inject
    private UserDAOJPA userDAOJPA;

    @PostConstruct
    private void seedData() {
        System.out.println("Seeding data");

        User user = new User("test", "test", "bio" , "website" , " location" );
        user.setRole(Role.ADMIN);
        userDAOJPA.create(user);

        for (int i = 0; i < 10; i++) {
            User u = new User("username" + i, "password" + i, "bio" + i, "website" + i, " location" + i);
            u.setRole(Role.USER);
            userDAOJPA.create(u);
            try {
                userService.follow(u.getUsername(), "test");
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(u.getRole().toString());
        }
    }

}


