package service;

import dao.DataSeeder;
import dao.UserDAOJPA;
import exceptions.MessageTooLongException;
import exceptions.UserNotFoundException;
import exceptions.UsernameTakenException;
import models.Role;
import models.User;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UserService {
    @Inject
    UserDAOJPA userDAO;

    @Inject
    DataSeeder dataSeeder;


    public void editName(String username, String desired) throws UserNotFoundException, UsernameTakenException {

        User user = userDAO.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (userDAO.findByUsername(desired) == null) {
            user.setUsername(desired);
            userDAO.update(user);

        } else {
            throw new UsernameTakenException();
        }

    }

    public void editProfile(String username, String bio, String location, String website) throws UserNotFoundException, MessageTooLongException {
        User user = userDAO.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (bio.length() < 160) {
            user.setBio(bio);
            user.setLocation(location);
            user.setWebsite(website);
            userDAO.update(user);

        } else {
            throw new MessageTooLongException();
        }

    }

    public void follow(String follower, String followed) throws UserNotFoundException {

        User userFollower = userDAO.findByUsername(follower);
        User userFollowed = userDAO.findByUsername(followed);

        if (userFollowed == null || userFollower == null) {
            throw new UserNotFoundException();
        }

        userFollower.addFollowing(userFollowed);
        userFollowed.addFollower(userFollower);

        userDAO.update(userFollowed);
        userDAO.update(userFollower);

    }

    public boolean logIn(String username, String password) throws Exception {
        User user = userDAO.findByUsername(username);

        if (user == null || !password.equals(user.getPassword())) {
            System.out.println("password or user not good");
            throw new Exception(); //todo add specific exception handling
        }
        return true;
    }

    public void logOut() {

    }

    public boolean register(User user) throws UserNotFoundException {

        if (userDAO.findByUsername(user.getUsername()) == null) {
            User u = user;
            u.setRole(Role.USER);
            userDAO.create(user);

            return true;
        }
        return false;
    }

    public boolean promoteUserToModerator(String username) throws UserNotFoundException {
        User user = userDAO.findByUsername(username);

        if (user.getUsername() != null) {

            user.setRole(Role.MOD);
            userDAO.update(user);
            return true;
        }

        return false;
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public void deleteUser(String username) throws UserNotFoundException {
        userDAO.delete(getUserByUsername(username));
    }

    public User getUserById(int valueOf) throws UserNotFoundException {
        return userDAO.find(valueOf);
    }

}
