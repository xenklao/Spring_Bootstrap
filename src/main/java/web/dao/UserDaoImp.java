package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    RoleDao roleDao;


    @Override
    public User add(User t) {
        entityManager.persist(t);
        return t;
    }

    @Transactional
    @Override
    public User getUserByName(String name) {
        User user = (User) entityManager
                .createQuery("select u from User u where u.nickName = :n ")
                .setParameter("n", name).getSingleResult();
        System.out.println(user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return (User) entityManager.createQuery("select u from User u where u.id = :id ")
                .setParameter("id",id)
                .getSingleResult() ;
    }

    @Override
    public void remove(User t) {
        roleDao.removeByUserId(t.getId());
        entityManager.remove(entityManager.contains(t) ? t : entityManager.merge(t));
    }

    @Override
    public void remove(Long id) {
        roleDao.removeByUserId(id);
        entityManager.createQuery("delete from User u where u.id= :id")
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public User update(User t) {
        return entityManager.merge(t);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listData() {
        return entityManager.createQuery("select u from User u").getResultList();
    }
}
