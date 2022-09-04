package web.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="role", referencedColumnName = "role")
    private AllRoles allRoles;



    public Role() {

    }

    public Role(AllRoles allRoles) {
        this.allRoles = allRoles;
    }

    public Role(AllRoles allRoles,User user) {
        this.allRoles = allRoles;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AllRoles getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(AllRoles role) {
        this.allRoles = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return allRoles.getRole();
    }
}
