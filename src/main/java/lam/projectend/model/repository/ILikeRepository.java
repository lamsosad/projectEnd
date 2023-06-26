package lam.projectend.model.repository;

import lam.projectend.model.entity.page.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ILikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndPageId(Long idUser, Long idPage);

    void deleteLikeByUserIdAndPageId(Long idUser, Long idPage);

    Long countLikeByPageId(Long idPage);
}
