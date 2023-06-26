package lam.projectend.controller;

import lam.projectend.model.dto.request.SignInForm;
import lam.projectend.model.dto.request.SignUpForm;
import lam.projectend.model.dto.response.JwtResponse;
import lam.projectend.model.dto.response.ResponseMessage;
import lam.projectend.model.entity.user.RoleName;
import lam.projectend.model.entity.user.Roles;
import lam.projectend.model.entity.user.Users;
import lam.projectend.model.security.jwt.JwtProvider;
import lam.projectend.model.security.userPrincipal.UserPrincipal;
import lam.projectend.model.service.user.role.IRoleService;
import lam.projectend.model.service.user.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final IUserService userService;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> doSignUp(@Valid @RequestBody SignUpForm signUpForm, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("Dữ liệu không hợp lệ!")
                            .data("")
                            .build()
            );
        }
        boolean isExistUsername = userService.existsByUsername(signUpForm.getUsername());
        if (isExistUsername) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("Đã tồn tại tên đăng nhập!")
                            .data("")
                            .build()
            );
        }

        boolean isExistPhoneNumber = userService.existsByPhone(signUpForm.getPhone());
        if (isExistPhoneNumber) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("Đã tồn tại số điện thoại!")
                            .data("")
                            .build()
            );
        }


        Set<Roles> roles = new HashSet<>();
        if (signUpForm.getRoles() == null || signUpForm.getRoles().isEmpty()) {
            Roles role = roleService.findByName(RoleName.USER)
                    .orElseThrow(() -> new RuntimeException("Failed -> Không tìm thấy role phù hợp"));
            roles.add(role);
        } else {
            signUpForm.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByName(RoleName.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Failed -> Không tìm thấy role phù hợp"));
                        roles.add(adminRole);
                    case "user":
                        Roles userRole = roleService.findByName(RoleName.USER)
                                .orElseThrow(() -> new RuntimeException("Failed -> Không tìm thấy role phù hợp"));
                        roles.add(userRole);
                }
            });
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Users user = Users.builder()
                .fullName(signUpForm.getFullName())
                .username(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .phone(signUpForm.getPhone())
                .sex(signUpForm.isSex())
                .status(true)
                .avatar("https://static.vecteezy.com/system/resources/previews/008/442/086/original/illustration-of-human-icon-user-symbol-icon-modern-design-on-blank-background-free-vector.jpg")
                .birthDay(signUpForm.getBirthDay())
                .dateRegister(LocalDate.now().toString())
                .roles(roles)
                .build();

        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Đăng kí thành công")
                        .data(userService.save(user))
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> doSignIn(@RequestBody SignInForm signInForm) {
        try {
            Optional<Users> users = userService.findByUsername(signInForm.getUsername());
            if (!users.isPresent()) {
                users = userService.findByPhone(signInForm.getUsername());
                if (!users.isPresent()) {
                    return new ResponseEntity<>(
                            ResponseMessage.builder()
                                    .status("Failed")
                                    .message("Tài khoản chưa tồn tại!")
                                    .data("")
                                    .build(), HttpStatus.UNAUTHORIZED);
                }
            }
            if (users.get().isStatus() == false) {
                return new ResponseEntity<>(
                        ResponseMessage.builder()
                                .status("Failed")
                                .message("Tài khoản đã bị khóa!")
                                .data("")
                                .build(), HttpStatus.UNAUTHORIZED);
            }
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            users.get().getUsername(),
                            signInForm.getPassword())
                    );

            String token = jwtProvider.generateToken(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return new ResponseEntity<>(
                    JwtResponse.builder()
                            .status("OK")
                            .type("Bearer")
                            .id(userPrincipal.getId())
                            .fullName(userPrincipal.getFullName())
                            .token(token)
                            .roles(userPrincipal.getAuthorities())
                            .build(), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Mật khẩu hoặc tài khoản chưa chính xác!")
                            .data("")
                            .build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseMessage> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Nếu người dùng đã được xác thực, huỷ thông tin xác thực và đăng xuất
        if (authentication != null) {
            SecurityContextHolder.clearContext(); // Huỷ thông tin xác thực
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("OK")
                            .message("Đã dăng xuất!")
                            .data("")
                            .build()
            );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
