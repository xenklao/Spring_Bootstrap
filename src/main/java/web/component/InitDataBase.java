package web.component;

import org.springframework.stereotype.Component;
import web.model.AllRoles;
import web.model.Role;
import web.model.User;
import web.service.AllRolesService;
import web.service.RoleService;
import web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitDataBase {
    private final UserService userService;
    private final RoleService roleService;
    private final AllRolesService allRolesService;

    public InitDataBase(UserService userService, RoleService roleService, AllRolesService allRolesService) {
        this.userService = userService;
        this.roleService = roleService;
        this.allRolesService = allRolesService;
    }

    @PostConstruct
    private void postConstruct() {
        createAdmin();
        createUsers();
    }

    private void createAdmin() {
        User admin = new User("admin", "Ivan",
                "Ivanov", "admin", 33);
        AllRoles roleAdmin = new AllRoles("ROLE_ADMIN");
        AllRoles roleUser = new AllRoles("ROLE_USER");
        userService.addUser(admin);
        allRolesService.add(roleAdmin);
        allRolesService.add(roleUser);
        roleService.add(new Role(roleAdmin, admin));
    }

    private void createUsers() {

        userService.addUser(new User("test1", "TestName1", "TestLastName", "qwe", 22));
        userService.addUser(new User("test2", "TestName2", "TestLastName", "qwe", 22));
        userService.addUser(new User("test3", "TestName3", "TestLastName", "qwe", 22));
        userService.addUser(new User("test4", "TestName4", "TestLastName", "qwe", 22));
        userService.addUser(new User("test5", "TestName5", "TestLastName", "qwe", 22));
        userService.addUser(new User("test6", "TestName6", "TestLastName", "qwe", 22));

        roleService.add(new Role(allRolesService.getRoleById(2L), userService.getUserByName("test1")));
        roleService.add(new Role(allRolesService.getRoleById(2L), userService.getUserByName("test2")));
        roleService.add(new Role(allRolesService.getRoleById(2L), userService.getUserByName("test3")));
        roleService.add(new Role(allRolesService.getRoleById(2L), userService.getUserByName("test4")));
        roleService.add(new Role(allRolesService.getRoleById(2L), userService.getUserByName("test5")));
        roleService.add(new Role(allRolesService.getRoleById(2L), userService.getUserByName("test6")));
    }
}
