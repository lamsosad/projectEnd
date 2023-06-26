package lam.projectend.model.entity.message;

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
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String dateSend;
    @ManyToOne
    private Users usersSent;
    @ManyToOne
    private Users usersReceived;
}
