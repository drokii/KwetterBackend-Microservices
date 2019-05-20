package dao;

import exceptions.UserNotFoundException;

import models.User;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class UserDAOJPA extends EntityDAOJPA<User> {

    public UserDAOJPA() {
    }

    @Override
    public List<User> findAll() {
        List users = em.createQuery("SELECT u FROM User u").getResultList();
        if (users.isEmpty()) {
            System.out.println("User list empty");
        }
        return users;
    }

    @Override
    public User find(int id) throws UserNotFoundException {
        User user = em.find(User.class, id);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException();
        }

    }

    public User findByUsername(String username){
    try{
        List users = em.createQuery("SELECT u FROM User u WHERE u.username=:username")
                .setParameter("username", username)
                .getResultList();

        return (User) users.get(0);
    }
    catch (Exception e){
        return null;
    }


    }

    public List<Integer> getAllTweetsFromUser(String username) throws UserNotFoundException {
        try{
            return findByUsername(username).getTweets();
        }catch(Exception e){
            throw new UserNotFoundException();
        }

    }
}
