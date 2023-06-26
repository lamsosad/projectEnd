package lam.projectend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpForm {
    @Size(min = 6,max = 50)
    private String username;
    @Size(min = 8,max = 50)
    private String password;
    @Size(min = 6,max = 50)
    private String fullName;
    @Lob
    private String avatar;
    @Pattern(regexp = "^(03|09|08|02)\\d{8}$")
    private String phone;
    private boolean sex;
    private boolean status;
    private String birthDay;
    private String dateRegister;
    private Set<String> roles;

}