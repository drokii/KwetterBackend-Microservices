package rest;

import auth.Secured;
import auth.dtos.FollowerDTO;
import auth.dtos.UserDTO;
import auth.requests.EditUserRequest;
import auth.requests.FollowRequest;
import com.google.gson.Gson;
import exceptions.MessageTooLongException;
import exceptions.UserNotFoundException;
import exceptions.UsernameTakenException;
import models.User;
import services.UserService;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


@Produces({"application/json"})
@Path("user")
public class UserServiceRest {

    @Inject
    UserService userService;

    // TODO: GETTERS USING USERNAME NEED TO SHIFT TO ID
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") String id) throws UserNotFoundException {
        User user = userService.getUserById(Integer.parseInt(id));
        List<User> userList = new ArrayList<User>();
        userList.add(user);


        Gson gson = new Gson();
        String output = gson.toJson(convertIntoDTO(userList).get(0));
        return Response.status(200).entity(output).build();
    }

    @POST
    @Secured
    @Path("/edituser")
    public Response editUsername(EditUserRequest request) throws MessageTooLongException, UserNotFoundException {
        try {
            userService.editName(request.getUsername(), request.getDesiredUsername());
            return Response.status(200).entity("Profile and Username has been changed!").build();
        } catch (UserNotFoundException e) {
            return Response.status(200).entity("This user doesn't exist.").build();
        } catch (UsernameTakenException e) {
            userService.editProfile(request.getUsername(), request.getBio(), request.getLocation(), request.getWebsite());
            return Response.status(200).entity("Profile Edited.").build();
        }
    }

    @POST
    @Path("/editprofile")
    public Response editProfile(EditUserRequest request) {
        try {
            userService.editProfile(request.getUsername(), request.getBio(), request.getLocation(), request.getWebsite());
            return Response.status(200).entity("User details have been changed!").build();
        } catch (UserNotFoundException e) {
            return Response.status(200).entity("This user doesn't exist.").build();
        } catch (MessageTooLongException e) {
            return Response.status(200).entity("Bio is too long.").build();
        }

    }

    @POST
    @Path("/follow")
    public Response follow(FollowRequest request) {
        try {
            userService.follow(request.getUsername(), request.getFollowed());
            return Response.status(200).entity("User followed!").build();

        } catch (UserNotFoundException e) {
            return Response.status(200).entity("This user doesn't exist.").build();
        }

    }

    private List<UserDTO> convertIntoDTO(List<User> users) {
        List<UserDTO> dataObjectList = new ArrayList<>();
        List<FollowerDTO> followers = new ArrayList<>();
        List<FollowerDTO> following = new ArrayList<>();

        Set<String> noRepeatingFollowers= new TreeSet<>();
        Set<String> noRepeatingFollowing= new TreeSet<>();

        for (User user : users
        ) {

                for (User followed : user.getFollowing()) {
                    if (!noRepeatingFollowing.contains(followed.getUsername())) {
                        following.add(new FollowerDTO(followed.getUsername(), (int) followed.getId()));
                        noRepeatingFollowing.add(followed.getUsername());

                    }

                }
                for (User follower : user.getFollowers()) {
                    if (!noRepeatingFollowers.contains(follower.getUsername())) {
                        followers.add(new FollowerDTO(follower.getUsername(), (int) follower.getId()));
                        noRepeatingFollowers.add(follower.getUsername());
                    }
                }


            dataObjectList.add(new UserDTO(user.getBio(), user.getWebsite(), user.getLocation(), user.getUsername(), followers, following));
            noRepeatingFollowing.clear();
            noRepeatingFollowers.clear();

        }

        return dataObjectList;
    }


}
