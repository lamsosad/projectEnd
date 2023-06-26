package lam.projectend.model.service.page.like;

import lam.projectend.model.entity.page.Like;
import lam.projectend.model.repository.ILikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceIMPL implements ILikeService {
    private final ILikeRepository likeRepository;

    @Override
    public List<Like> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public Optional<Like> findById(Long id) {
        return likeRepository.findById(id);
    }

    @Override
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public void remove(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public boolean existsByUserIdAndPageId(Long idUser,Long idPage) {
        return likeRepository.existsByUserIdAndPageId(idUser,idPage);
    }

    @Override
    public Long countLikeByPageId(Long idPage) {
        return likeRepository.countLikeByPageId(idPage);
    }

    @Override
    public void deleteLikeByUserIdAndPageId(Long idUser, Long idPage) {
        likeRepository.deleteLikeByUserIdAndPageId(idUser,idPage);
    }
}
