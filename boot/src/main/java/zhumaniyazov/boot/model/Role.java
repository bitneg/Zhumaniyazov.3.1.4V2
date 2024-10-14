package zhumaniyazov.boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;


import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String roleName;


    public Role() {
    }


    public Role(String roleName) {

        this.roleName = roleName;
    }


    public String getRoleNameWithoutPrefix() {
        return roleName.substring(5);

    }


    @Override
    public String getAuthority() {
        return roleName;
    }



    public Role(long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getId() == role.getId() && Objects.equals(getRoleName(), role.getRoleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoleName());
    }
}
