package models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Tweet {
    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    private String message;

    @Temporal(TemporalType.DATE)
    private Date date;

    public Integer getAuthor() {
        return author;
    }

    @Column
    private Integer author;

    private String authorUsername;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return id == tweet.id &&
                Objects.equals(message, tweet.message) &&
                Objects.equals(date, tweet.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, date);
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
