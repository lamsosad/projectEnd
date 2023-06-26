package lam.projectend.model.repository;

import lam.projectend.model.entity.user.RoleName;
import lam.projectend.model.entity.user.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByName(RoleName name);
}
