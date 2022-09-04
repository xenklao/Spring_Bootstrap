package web.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public User addUser(User user) {
        return userDao.add(user);
    }

    @Transactional
    @Override
    public void removeUser(User user) {
        userDao.remove(user);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userDao.remove(id);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        return userDao.update(user);
    }

    @Transactional
    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public List<User> listUsers() {
        return userDao.listData();
    }

}
