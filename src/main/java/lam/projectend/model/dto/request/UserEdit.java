package lam.projectend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEdit {
    @Size(min = 6, max = 50)
    private String fullName;
    @Pattern(regexp = "^(03|09|08|02)\\d{8}$")
    private String phone;
    @Email
    private String email;
    private String birthDay;
    private String address;
    private boolean status;
}
