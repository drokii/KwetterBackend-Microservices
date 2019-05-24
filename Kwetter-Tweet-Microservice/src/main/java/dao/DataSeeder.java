package dao;

import exceptions.MessageTooLongException;
import exceptions.UserNotFoundException;
import rest.TweetServiceRest;
import service.TweetService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class DataSeeder {

    @Inject
    private TweetService tweetService;

    @PostConstruct
    private void seedData(){
        for (int i = 0; i < 10; i++) {
            try {
                tweetService.postTweet(i, "username"+i, "This is a test message.");
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            } catch (MessageTooLongException e) {
                e.printStackTrace();
            }
            System.out.println("Tweet posted");
        }
    }

}
