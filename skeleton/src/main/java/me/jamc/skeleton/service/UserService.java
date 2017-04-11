package me.jamc.skeleton.service;

import java.util.List;

import me.jamc.skeleton.model.User;

public interface UserService {

    public User addUser(String firstName, String lastName);
    public User updateUser(int id, String firstName, String lastName);
    public User getUser(int id);
    public User deleteUser(int id);
    public List<User> getAllUsers();

}
