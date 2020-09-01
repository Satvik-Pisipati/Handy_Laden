package ch.bzz.phoneshop.model;


/**
 * The type User.
 */
public class User {
    private String username;
    private String userRole;

    /**
     * Instantiates a new User.
     */
    public User() {
        setUserRole("guest");
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets user role.
     *
     * @return the user role
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Sets user role.
     *
     * @param userRole the user role
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}




