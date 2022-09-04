package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.model.Role;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService{

    @Autowired
    RoleDao roleDao;

    @Override
    public List<Role> getRolesByUid(Long userUd) {
        return roleDao.getRolesByUid(userUd);
    }

    @Transactional
    @Override
    public void add(Role role) {
        roleDao.add(role);
    }
}
