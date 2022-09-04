package web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.AllRoles;
import web.model.Role;
import web.model.User;
import web.responses.Flag;
import web.service.AllRolesService;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("")
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    AllRolesService allRolesService;

    @GetMapping(value = "/")
    @ResponseBody
    public String getIndex() {
        return "MAIN PAGE";
    }

    @GetMapping(value = "/login")
    public String loginPAge(ModelMap modelMap) {
        return "login";
    }

    @GetMapping(value = "/user")
    public String getUserPage(Principal principal, ModelMap map) {
        User user = userService.getUserByName(principal.getName());
        map.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/admin")
    public String getIndexUsers(Principal principal, ModelMap modelMap) {
        User user = userService.getUserByName(principal.getName());
        List<User> userList = userService.listUsers();
        userList.remove(user);
        modelMap.addAttribute("users", userList);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("allRoles", allRolesService.getAllRoles());
        return "admin";

    }

    @GetMapping(value = "/hello")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @PostMapping(value = "/addUser",
            consumes = "application/json;",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping(value = "/updateUser",
            consumes = "application/json",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping(value = "/removeUser",
            consumes = "application/json",
            produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeUser(@RequestBody User user) {
        userService.removeUser(user);
    }

    @DeleteMapping(value = "/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        userService.removeUser(id);
    }

    /**
     * @param data body of request "uid":Long, "rid":Long, "role":String
     * @return json file "flag":Boolean
     * @see "uid":"1","rid":"1","role":"ROLE_USER"
     * @see "flag":"true"
     */
    @PostMapping(value = "/addRole",
            consumes = "application/json;",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addRole(@RequestBody String data) {
        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
        List<Role> roleList = roleService.getRolesByUid(jsonObject.get("uid").getAsLong());
        String roleFromJson = jsonObject.get("role").getAsString();

        for (Role role : roleList)
            if (roleFromJson.equals(role.getAllRoles().getRole()))
                return Flag.FALSE;

        User user = userService.getUserById(jsonObject.get("uid").getAsLong());
        AllRoles allRoles = allRolesService.getRoleById(jsonObject.get("rid").getAsLong());
        roleService.add(new Role(allRoles, user));

        return Flag.TRUE;

    }

}
