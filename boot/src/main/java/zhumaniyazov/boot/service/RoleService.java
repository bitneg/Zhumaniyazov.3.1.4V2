package zhumaniyazov.boot.service;

import zhumaniyazov.boot.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(long id);
    Role save(Role role);
    Role findByName(String name);
}