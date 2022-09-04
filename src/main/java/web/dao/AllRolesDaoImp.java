package web.dao;

import org.springframework.stereotype.Repository;
import web.model.AllRoles;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AllRolesDaoImp implements AllRolesDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(AllRoles allRoles) {
        entityManager.persist(allRoles);
    }

    @Override
    public AllRoles getRoleById(Long roleId) {
        return (AllRoles) entityManager.createQuery("select ar from AllRoles ar where ar.id = :roleId")
                .setParameter("roleId",roleId)
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AllRoles> getAllRoles() {
        List<AllRoles> allRoles = (List<AllRoles>) entityManager.createQuery("select ar from AllRoles ar").getResultList();
        return allRoles;
    }
}
