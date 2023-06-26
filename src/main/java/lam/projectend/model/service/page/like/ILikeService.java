package lam.projectend.model.service.page.like;

import lam.projectend.model.entity.page.Like;
import lam.projectend.model.service.IService;

public interface ILikeService extends IService<Like,Long> {
    boolean existsByUserIdAndPageId(Long idUser,Long idPage);

    Long countLikeByPageId(Long idPage);
    void deleteLikeByUserIdAndPageId(Long idUser, Long idPage);

}
