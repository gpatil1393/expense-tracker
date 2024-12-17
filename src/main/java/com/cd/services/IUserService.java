package com.cd.services;

import com.cd.models.AuthUser;
import com.cd.models.User;

public interface IUserService {
    public User findUserById(int id);
    public User findUserByEmail(String email);
    public User findUserByUsername(String username);
    public User createUser(User user);
    public AuthUser validateUserCredentials(String email, String password);
}
