package lam.projectend.model.service.user.friend;

import lam.projectend.model.entity.friend.Friend;
import lam.projectend.model.repository.IFriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendServiceIMPL implements IFriendService {
    @Override
    public List<Friend> findFriendByAlertAndUsersId(boolean alert, Long idUser) {
        return friendRepository.findFriendByAlertAndUsersId(alert, idUser);
    }

    private final IFriendRepository friendRepository;

    @Override
    public List<Friend> findAll() {
        return friendRepository.findAll();
    }

    @Override
    public Optional<Friend> findById(Long id) {
        return friendRepository.findById(id);
    }

    @Override
    public Friend save(Friend friend) {
        return friendRepository.save(friend);
    }

    @Override
    public void remove(Long id) {
        friendRepository.deleteById(id);
    }

    @Override
    public boolean existsFriendByUsersIdAndFriendId(Long idUser, Long idFriend) {
        return friendRepository.existsFriendByUsersIdAndFriendId(idUser, idFriend);
    }


    @Override
    public List<Friend> findFriendByStatusAndUsersId(boolean status, Long idUser) {
        return friendRepository.findFriendByStatusAndUsersId(status, idUser);
    }

    @Override
    public void deleteByUsersIdAndFriendId(Long idUser, Long idFriend) {
        friendRepository.deleteByUsersIdAndFriendId(idUser, idFriend);
    }

    @Override
    public Friend findFriendByUsersIdAndFriendId(Long idUser, Long idFriend) {
        return friendRepository.findFriendByUsersIdAndFriendId(idUser, idFriend);
    }
}
