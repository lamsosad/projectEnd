package lam.projectend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeDTO {
    private Long id;
    private Long idUser;
    private Long idPage;
    private boolean status;
}
