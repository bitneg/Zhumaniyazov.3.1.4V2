package zhumaniyazov.boot.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import zhumaniyazov.boot.model.Role;
import zhumaniyazov.boot.repository.RoleRepository;

import java.util.List;
@Getter
@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository repositories;

    public RoleServiceImpl(RoleRepository repositories) {
        this.repositories = repositories;
    }

    @Override
    public List<Role> getAllRoles() {
        return repositories.findAll();
    }

    @Override
    public Role getRoleById(long id) {
        return repositories.getById(id);
    }

}
