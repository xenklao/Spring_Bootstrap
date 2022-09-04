package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    User add(User t);

    void remove(User t);

    void remove(Long id);

    User update(User t);

    List<User> listData();

    User getUserByName(String name);

    User getUserById(Long id);

}
