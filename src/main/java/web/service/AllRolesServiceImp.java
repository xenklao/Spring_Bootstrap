package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AllRolesDao;
import web.model.AllRoles;

import java.util.List;

@Service
@Transactional
public class AllRolesServiceImp implements AllRolesService{

    @Autowired
    AllRolesDao allRolesDao;

    @Override
    public void add(AllRoles allRoles) {
        allRolesDao.add(allRoles);
    }

    @Override
    public AllRoles getRoleById(Long roleId) {
        return allRolesDao.getRoleById(roleId);
    }

    @Override
    public List<AllRoles> getAllRoles() {
        return allRolesDao.getAllRoles();
    }
}
