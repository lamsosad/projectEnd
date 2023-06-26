package lam.projectend.model.service.user.role;

import lam.projectend.model.entity.user.RoleName;
import lam.projectend.model.entity.user.Roles;

import java.util.Optional;

public interface IRoleService {
    Optional<Roles> findByName(RoleName name);
}
