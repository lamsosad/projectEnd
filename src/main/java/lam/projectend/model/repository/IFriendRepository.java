package lam.projectend.model.repository;

import lam.projectend.model.entity.friend.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IFriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findFriendByStatusAndUsersId(boolean status, Long idUser);

    List<Friend> findFriendByAlertAndUsersId(boolean alert, Long idUsers);

    Friend findFriendByUsersIdAndFriendId(Long idUser, Long idFriend);

    List<Friend> findFriendByStatusAndAlert(boolean status, boolean alert);


    boolean existsFriendByUsersIdAndFriendId(Long idUser, Long idFriend);

    @Modifying
//    @Transactional
    @Query("delete from Friend as f where f.users.id=?1 and f.friend.id=?2")
    void deleteByUsersIdAndFriendId(Long idUser, Long idFriend);
}
