package lam.projectend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendAddFriend {
    private Long idUser;
    private Long idFriend;
    private boolean alert;
}
