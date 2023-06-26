package lam.projectend.controller;

import lam.projectend.model.dto.request.ChangeAvatar;
import lam.projectend.model.dto.request.PasswordDTO;
import lam.projectend.model.dto.request.UserEdit;
import lam.projectend.model.dto.request.UserStatus;
import lam.projectend.model.dto.response.ResponseMessage;
import lam.projectend.model.entity.user.Users;
import lam.projectend.model.security.userPrincipal.UserPrincipal;
import lam.projectend.model.service.user.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("")
    public Page<Users> findAllUser(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size) {

        Page<Users> usersPage = userService.findAll(page,size);
        return usersPage;
    }


    @GetMapping("{id}")
    public ResponseEntity<Users> findUserById(@PathVariable Long id) {
        Optional<Users> users = userService.findById(id);
        if (!users.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users.get(), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMessage> update(@PathVariable Long id, @RequestBody UserEdit userEdit) {
        Users usersOptional = userService.findById(id).get();
        if (usersOptional != null) {
            usersOptional.setFullName(userEdit.getFullName());
            usersOptional.setBirthDay(userEdit.getBirthDay());
            usersOptional.setEmail(userEdit.getEmail());
            usersOptional.setPhone(userEdit.getPhone());
            usersOptional.setAddress(userEdit.getAddress());
            userService.save(usersOptional);
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("OK")
                            .message("Cập nhật thành công")
                            .data("")
                            .build()
            );
        }

        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("Failed")
                        .message("Không thể cập nhật!")
                        .data("")
                        .build()
        );


    }

    @PutMapping("/changePass/{id}")
    public ResponseEntity<ResponseMessage> changePassword(@PathVariable Long id,
                                                          @RequestBody PasswordDTO passwordDTO) {
        Optional<Users> usersOptional = userService.findById(id);
        if (passwordEncoder.matches(passwordDTO.getOldPass(), usersOptional.get().getPassword())) {
            if (passwordDTO.getNewPass().equals(passwordDTO.getReNewPass())) {
                usersOptional.get().setPassword(passwordEncoder.encode(passwordDTO.getNewPass()));
                userService.save(usersOptional.get());
                return ResponseEntity.ok().body(
                        ResponseMessage.builder()
                                .status("OK")
                                .message("Thay đổi mật khẩu thành công!")
                                .data("")
                                .build()
                );
            }
        }
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("Failed")
                        .message("Không trùng khớp mật khẩu!")
                        .data("")
                        .build()
        );
    }

    @PutMapping("changeAvatar/{id}")
    public ResponseEntity<ResponseMessage> changeAvatar(@PathVariable Long id, @RequestBody ChangeAvatar changeAvatar) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userPrincipal.getId().equals(id)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Không được phép!")
                            .data("")
                            .build()

            );
        }

        Users users = userService.findById(id).get();
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        users.setAvatar(changeAvatar.getImageUrls());
        userService.save(users);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Thay đổi thành công!")
                        .data("")
                        .build()
        );
    }
    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<ResponseMessage> changeStatus(@PathVariable Long id, @RequestBody UserStatus userStatus) {
        Users users = userService.findById(id).get();
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (users.getRoles().size() == 2) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Không thể thay đổi!")
                            .data("")
                            .build()
            );
        }
        users.setStatus(userStatus.isStatus());
        userService.save(users);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Thay đổi thành công!")
                        .data("")
                        .build()
        );

    }
    @GetMapping("/searchFullName")
    public ResponseEntity<ResponseMessage> searchUser(@RequestParam String search) {
        List<Users> users = userService.searchByFullName(search);
        if (users.isEmpty()) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Không tìm thấy bài viết!")
                            .data("")
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("")
                        .data(users)
                        .build()
        );
    }


}
