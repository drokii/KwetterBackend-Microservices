package dao;

import exceptions.TweetNotFoundException;
import exceptions.UserNotFoundException;

import java.util.List;

public interface EntityDAO<T> {
    void create(T t);
    void delete(T t);
    void update(T t);
    T find(int id) throws UserNotFoundException, TweetNotFoundException;
    List<T> findAll();

}
