package lam.projectend.model.security.userPrincipal;

import lam.projectend.model.entity.user.Users;
import lam.projectend.model.service.user.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final IUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("OOP! Không tìm thấy username: " + username));

        return UserPrincipal.build(user);
    }

    public Users getUsersPrincipal(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userPrincipal.getId();
        return userService.findById(id).get();
    }
}
