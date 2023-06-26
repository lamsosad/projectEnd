package lam.projectend.model.service.user.friend;

import lam.projectend.model.entity.friend.Friend;
import lam.projectend.model.service.IService;

import java.util.List;

public interface IFriendService extends IService<Friend,Long> {
    boolean existsFriendByUsersIdAndFriendId(Long idUser, Long idFriend);
    Friend findFriendByUsersIdAndFriendId(Long idUser, Long idFriend);
    List<Friend> findFriendByAlertAndUsersId(boolean alert,Long idUser);
    List<Friend> findFriendByStatusAndUsersId(boolean status,Long idUser);
    void deleteByUsersIdAndFriendId(Long idUser, Long idFriend);
}
