package lam.projectend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeAvatar {
    @Lob
    private String imageUrls;
}
