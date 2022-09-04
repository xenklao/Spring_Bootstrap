package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    void removeUser(User user);
    void removeUser(Long id);
    User updateUser(User user);
    User getUserByName(String name);
    User getUserById(Long id);
    List<User> listUsers();

}
