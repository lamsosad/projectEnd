package lam.projectend.model.service.page.page;

import lam.projectend.model.entity.page.Page;
import lam.projectend.model.service.IService;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPageService extends IService<Page,Long> {
    List<Page> findByUsersIdAndStatusOrderByDatePostDesc(Long id,boolean status);
    List<Page> searchByTitle(String title);

    List<Page> findPageByStatusAndRegimeOrderByDatePostDesc(boolean status,boolean regime);
    List<Page> findByUsersIdAndStatusAndRegimeOrderByDatePostDesc(Long id,boolean status,boolean regime);

}
