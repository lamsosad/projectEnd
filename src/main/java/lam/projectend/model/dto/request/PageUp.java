package lam.projectend.model.dto.request;

import lam.projectend.model.entity.user.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageUp {
    private String title;
    @Lob
    private String imageUrls;
    private String datePost;
    private boolean status;
    private boolean regime;
    private int countLike;
    private int countCmt;
    private Users users;
}
