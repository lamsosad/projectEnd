package lam.projectend.model.service.user.role;

import lam.projectend.model.entity.user.RoleName;
import lam.projectend.model.entity.user.Roles;
import lam.projectend.model.repository.IRoleRepository;
import lam.projectend.model.service.user.role.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceIMPL implements IRoleService {
    private final IRoleRepository roleService;
    @Override
    public Optional<Roles> findByName(RoleName name) {
        return roleService.findByName(name);
    }
}
