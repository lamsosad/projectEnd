package lam.projectend.model.repository;

import lam.projectend.model.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message,Long> {
    List<Message> findMessageByUsersSentIdAndUsersReceivedIdOrderByDateSendDesc(Long idUsersSent,Long idUsersReceivedId);
}
