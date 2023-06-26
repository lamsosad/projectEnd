package lam.projectend.model.repository;

import lam.projectend.model.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    Optional<Users> findByUsername(String username);

    Optional<Users> findByPhone(String phone);
    @Query("select u from Users as u where u.fullName like concat('%',upper(?1) ,'%')")
    List<Users> searchByFullName(String name);
}
