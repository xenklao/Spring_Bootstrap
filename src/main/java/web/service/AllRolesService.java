package web.service;

import web.model.AllRoles;

import java.util.List;

public interface AllRolesService {
    void add(AllRoles allRoles);
    AllRoles getRoleById(Long roleId);
    List<AllRoles> getAllRoles();
}
