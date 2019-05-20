package dao;

import exceptions.TweetNotFoundException;
import models.Tweet;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TweetDAOJPA extends EntityDAOJPA<Tweet> {

    public TweetDAOJPA() {
    }

    @Override
    public Tweet find(int id) throws TweetNotFoundException {
        Tweet tweet = em.find(Tweet.class, id);
        if (tweet != null){
            return tweet;
        }else{
            throw new TweetNotFoundException();
        }

    }

    @Override
    public List<Tweet> findAll() {
        List tweets = em.createQuery("SELECT t FROM Tweet t").getResultList();
        return tweets;
    }

    public List<Tweet> findPerId(Integer id) {
        List tweets = em.createQuery("SELECT t FROM Tweet t where t.author =:id").setParameter("id", id).getResultList();
        return tweets;
    }


}
