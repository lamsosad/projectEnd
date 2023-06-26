package lam.projectend.model.service.user.user;

import lam.projectend.model.entity.user.Users;
import lam.projectend.model.repository.IUserRepository;
import lam.projectend.model.service.user.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements IUserService {
    private final IUserRepository userRepository;

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<Users> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<Users> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<Users> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }


    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Users save(Users user) {
        return userRepository.save(user);
    }

    @Override
    public List<Users> searchByFullName(String name) {
        return userRepository.searchByFullName(name);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}
