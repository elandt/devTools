package com.elandt.lil.ec.web.dto;

import javax.validation.constraints.NotNull;

public class LoginDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String firstName;

    private String lastName;

    protected LoginDto() {
        // Default constructor - protected to force use of contructor below
    }

    /**
     * Full constructor
     *
     * @param username username of the user attempting to login
     * @param password password of the user attempting to login
     */
    public LoginDto(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


}
