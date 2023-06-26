package lam.projectend.model.repository;


import lam.projectend.model.entity.page.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPageRepository extends JpaRepository<Page, Long> {
    List<Page> findPageByStatusAndRegimeOrderByDatePostDesc(boolean status,boolean regime);

    List<Page> findByUsersIdAndStatusOrderByDatePostDesc(Long id,boolean status);
    List<Page> findByUsersIdAndStatusAndRegimeOrderByDatePostDesc(Long id,boolean status,boolean regime);
    @Query("select p from Page as p where p.title like concat('%',upper(?1) ,'%')")
    List<Page> searchByTitle( String title);
}
