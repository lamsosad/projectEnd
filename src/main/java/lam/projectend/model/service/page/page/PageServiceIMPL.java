package lam.projectend.model.service.page.page;

import lam.projectend.model.entity.page.Page;
import lam.projectend.model.repository.IPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageServiceIMPL implements IPageService {

    private final IPageRepository pageRepository;

    @Override
    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    @Override
    public Optional<Page> findById(Long id) {
        return pageRepository.findById(id);
    }

    @Override
    public Page save(Page page) {
        return pageRepository.save(page);
    }

    @Override
    public void remove(Long id) {
        pageRepository.deleteById(id);
    }



    @Override
    public List<Page> findByUsersIdAndStatusOrderByDatePostDesc(Long id, boolean status) {
        return pageRepository.findByUsersIdAndStatusOrderByDatePostDesc(id,status);
    }

    @Override
    public List<Page> searchByTitle(String title) {
        return pageRepository.searchByTitle(title);
    }



    @Override
    public List<Page> findPageByStatusAndRegimeOrderByDatePostDesc(boolean status, boolean regime) {
        return pageRepository.findPageByStatusAndRegimeOrderByDatePostDesc(status,regime);
    }

    @Override
    public List<Page> findByUsersIdAndStatusAndRegimeOrderByDatePostDesc(Long id, boolean status, boolean regime) {
        return pageRepository.findByUsersIdAndStatusAndRegimeOrderByDatePostDesc(id,status,regime);
    }
}
