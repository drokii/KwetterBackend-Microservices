package auth.dtos;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {
    String bio;
    String website;
    String location;
    String username;
    List<FollowerDTO> followers;
    List<FollowerDTO> following;

    public UserDTO(String bio, String website, String location, String username, List<FollowerDTO> followers, List<FollowerDTO> following) {
        this.bio = bio;
        this.website = website;
        this.location = location;
        this.username = username;
        this.followers = followers;
        this.following = following;
    }



}
