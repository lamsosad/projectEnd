package lam.projectend.model.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Size(min = 6,max = 50)
    @JsonIgnore
    private String username;
    @Size(min = 6,max = 50)
    private String fullName;
    private String phone;
    @Email
    private String email;
    @Lob
    private String avatar;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(columnDefinition = "date")
    private String birthDay;
    private String address;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(columnDefinition = "date")
    private String dateRegister;
    private boolean sex;
    private boolean status;
    @JsonIgnore
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Roles> roles = new HashSet<>();


}
