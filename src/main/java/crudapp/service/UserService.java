package crudapp.service;

import crudapp.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User readUser(long id);

    User deleteUser(long parseUnsignedInt);

    void createUser(User user);

    void updateUser(User user);


//    void createOrUpdateUser(User user);
}
