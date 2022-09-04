package web.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickName;
    @Column
    private String firstName;
    @Column
    private String lastName;

    @Column
    private String password;

    @Column
    private int age;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    public User(String nickName, String firstName, String lastName, String password, int age) {
        this.nickName = nickName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
    }

    public User(String nickName, String firstName, String lastName, String password, int age, Set<Role> roles) {
        this(nickName, firstName, lastName, password, age);
        this.roles = roles;
    }

    public User(Long id, String nickName, String firstName, String lastName, String password, int age) {
        this(nickName, firstName, lastName, password, age);
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
//
//    public boolean isAdmin() {
//        for (Role role : roles) {
//            if (role.getRole().equals("ROLE_ADMIN")) return true;
//        }
//        return false;
//    }
//
//    public String getAllRolesLikeString(){
//        StringBuilder sb = new StringBuilder();
//        for ( Role role :roles){
//            sb.append(role.getRole()).append(" ");
//        }
//        return sb.toString();
//    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
