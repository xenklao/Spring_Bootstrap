package web.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class AllRoles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String role;

    @OneToMany
    private Set<Role> roles;

    public AllRoles() {
    }

    public AllRoles(String role){
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
