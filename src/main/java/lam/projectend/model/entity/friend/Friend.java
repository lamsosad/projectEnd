package lam.projectend.model.entity.friend;

import lam.projectend.model.entity.user.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
    private boolean alert;
    private String dateAdd;
    @ManyToOne
    private Users users;
    @ManyToOne
    private Users friend;

}
