package web.dao;

import web.model.AllRoles;
import web.model.Role;

import java.util.List;

public interface AllRolesDao {
    void add(AllRoles role);
    AllRoles getRoleById(Long roleId);
    List<AllRoles> getAllRoles();
}
