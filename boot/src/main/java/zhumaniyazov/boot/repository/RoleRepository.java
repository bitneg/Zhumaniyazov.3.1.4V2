package zhumaniyazov.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zhumaniyazov.boot.model.Role;
import zhumaniyazov.boot.model.User;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
