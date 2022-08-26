package securitycrudapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.transaction.annotation.Transactional;
import securitycrudapp.config.exception.LoginException;
import securitycrudapp.model.Role;
import securitycrudapp.model.User;
import securitycrudapp.repository.RoleRepository;
import securitycrudapp.repository.UserRepository;
import securitycrudapp.service.RoleServiceImpl;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;
//import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleRepository;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", email))
        );
    }
//    @Transactional(readOnly=true)
//    @Override
//    public List<Role> findAllRoles() {
//        return roleRepository.findAll();
//    }

//    @Transactional(readOnly = true)
//    @Override
//    public void authenticateOrLogout(Model model, HttpSession session, LoginException authenticationException, String authenticationName) {
//        if (authenticationException != null) { // Восстанавливаем неверно введенные данные
//            try {
//                model.addAttribute("authenticationException", authenticationException);
//                session.removeAttribute("Authentication-Exception");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            model.addAttribute("authenticationException", new LoginException(null));
//        }
//
//        if (authenticationName != null) { // Выводим прощальное сообщение
//            try {
//                model.addAttribute("authenticationName", authenticationName);
//                session.removeAttribute("Authentication-Name");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
    @Transactional(readOnly = true)
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName", "lastName"));
    }
    @Transactional(readOnly = true)
    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public boolean saveUser(User user, BindingResult bindingResult, Model model) {
        model.addAttribute("allRoles", roleRepository.findAllRoles());

        if (bindingResult.hasErrors()) {
            return false;
        }

        /*for (Role role : user.getRoles()) {
            Optional<Role> optionalRole = roleRepository.findByName(role.getAuthority());
            optionalRole.ifPresent(value -> role.setId(value.getId()));
        }*/
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userRepository.save(user);
        } catch (PersistenceException e) { // org.hibernate.exception.ConstraintViolationException
            model.addAttribute("persistenceException", true);
            return false;
        }

        return true;
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            try {
                userRepository.delete(user.get());
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        }
    }
}
