package lam.projectend.model.repository;

import lam.projectend.model.entity.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message,Long> {
    @Query(nativeQuery = true,value = "select * from message m where (m.usersReceived_id=:sent and m.usersSent_id=:receive) or (m.usersReceived_id=:receive and m.usersSent_id=:sent) order by m.dateSend desc")
    Page<Message> findMessageQuery(@Param("sent") Long idUsersSent,@Param("receive") Long idUsersReceivedId, Pageable pageable);
}
