package service;

import dao.TweetDAOJPA;
import exceptions.MessageTooLongException;
import exceptions.UserNotFoundException;
import models.Tweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class TweetService {
    @Inject
    TweetDAOJPA tweetDAO;


    public void postTweet(Integer id, String username, String message) throws UserNotFoundException, MessageTooLongException {

        if (message.length() < 160) {

            Date date = new Date();
            date.setTime(Calendar.getInstance().getTimeInMillis());

            Tweet tweet = new Tweet();
            tweet.setDate(date);
            tweet.setMessage(message);

            tweet.setAuthor(id);
            tweet.setAuthorUsername(username);

            tweetDAO.create(tweet);

        } else {
            throw new MessageTooLongException();
        }
    }

    public List<Tweet> getTweetsFromUser(Integer id) throws UserNotFoundException {
        return tweetDAO.findPerId(id);
    }

    public List<Tweet> lookForTweet(String search) {
        List<Tweet> searchResult = new ArrayList<>();
        List<Tweet> tweets = tweetDAO.findAll();
        for (Tweet t :
                tweets) {
            if (t.getMessage().contains(search)) {
                searchResult.add(t);
            }
        }
        return searchResult;
    }


    public void delete(Tweet tweet) {
        tweetDAO.delete(tweet);
    }

    public List<Tweet> getAllTweets() {
        return tweetDAO.findAll();
    }
}
