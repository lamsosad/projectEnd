package lam.projectend.model.service.page.cmt;

import lam.projectend.model.entity.page.Comment;
import lam.projectend.model.repository.ICommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceIMPL implements ICommentService {
    private final ICommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void remove(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findCommentByPageId(Long idPage) {
        return commentRepository.findCommentByPageId(idPage);
    }

    @Override
    public Long countCommentByPageId(Long idPage) {
        return commentRepository.countCommentByPageId(idPage);
    }

    @Override
    public void deleteCommentByIdAndUserIdAndPageId(Long id, Long idUser, Long idPage) {
        commentRepository.deleteCommentByIdAndUserIdAndPageId(id, idUser, idPage);
    }
}
