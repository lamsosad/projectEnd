package lam.projectend.model.service.user.user;

import lam.projectend.model.entity.user.Users;
import lam.projectend.model.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IService<Users,Long> {
    Page<Users> findAll(Pageable pageable);
    Page<Users> findAll(int page , int size);
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByPhone(String phone);
    Users save(Users user);
    List<Users> searchByFullName(String name);

}
