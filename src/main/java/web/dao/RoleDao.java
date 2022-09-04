package web.dao;

import web.model.AllRoles;
import web.model.Role;
import web.model.User;

import java.util.List;

public interface RoleDao {
    void add(Role role);
    List<Role> getRolesByUid(Long userUd);
    void removeByUserId(Long userId);
}
