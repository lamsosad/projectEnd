package lam.projectend.model.repository;

import lam.projectend.model.entity.page.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ICommentRepository extends JpaRepository<Comment,Long> {
    List<Comment>findCommentByPageId(Long idPage);
    Long countCommentByPageId(Long idPage);
    void deleteCommentByIdAndUserIdAndPageId(Long id,Long idUser, Long idPage);
}
