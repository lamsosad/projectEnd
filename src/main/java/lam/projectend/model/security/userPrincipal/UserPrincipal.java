package lam.projectend.model.security.userPrincipal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lam.projectend.model.entity.user.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrincipal  implements UserDetails {
    private Long id;

    private String username;

    private String fullName;

    private String phone;

    private String email;

    private String avatar;
    private String birthDay;
    private String address;
    private String dateRegister;
    private boolean sex;
    private boolean status;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> roles;

    public static UserPrincipal build(Users user) {

        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .birthDay(user.getBirthDay())
                .address(user.getAddress())
                .email(user.getEmail())
                .dateRegister(user.getDateRegister())
                .sex(user.isSex())
                .status(user.isStatus())
                .roles(grantedAuthorities)
                .build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
