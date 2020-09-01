package ch.bzz.phoneshop.service;

import ch.bzz.phoneshop.model.User;
import ch.bzz.phoneshop.data.DataHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.*;


/**
 * The type User service.
 */
@Path("user")
public class UserService {

    /**
     * Login response.
     *
     * @param username the username
     * @param password the password
     * @return the response
     */
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        int httpStatus;

        User user = DataHandler.readUser(username, password);
        if (user.getUserRole().equals("guest")) {
            httpStatus = 404;
        } else {
            httpStatus = 200;
        }

        NewCookie cookie = new NewCookie(
                "userRole",
                ((User)user).getUserRole(),
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .cookie(cookie)
                .entity("")
                .build();
        return response;
    }

    /**
     * Logout response.
     *
     * @return the response
     */
    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout() {
        NewCookie cookie = new NewCookie(
                "userRole",
                "guest",
                "/",
                "",
                "Login-Cookie",
                1,
                false
        );
        Response response = Response
                .status(200)
                .cookie(cookie)
                .entity("")
                .build();
        return response;
    }

}
