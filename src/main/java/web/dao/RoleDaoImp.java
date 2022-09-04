package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.AllRoles;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImp implements  RoleDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getRolesByUid(Long userId) {
        return (List<Role>) entityManager
                .createQuery("select distinct r from Role r where r.user.id = :uid")
                .setParameter("uid",userId).getResultList();
    }

    @Override
    public void removeByUserId(Long userId) {
        entityManager.createQuery("delete from Role r where r.user.id = :uid ")
                .setParameter("uid",userId).executeUpdate();
    }
}
