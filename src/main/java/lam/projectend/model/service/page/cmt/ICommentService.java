package lam.projectend.model.service.page.cmt;

import lam.projectend.model.entity.page.Comment;
import lam.projectend.model.service.IService;

import java.util.List;

public interface ICommentService extends IService<Comment,Long> {
    List<Comment> findCommentByPageId(Long idPage);
    Long countCommentByPageId(Long idPage);
    void deleteCommentByIdAndUserIdAndPageId(Long id,Long idUser, Long idPage);

}
