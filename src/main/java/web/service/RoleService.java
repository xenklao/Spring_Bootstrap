package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {
    void add(Role role);
    List<Role> getRolesByUid(Long userUd);
}
