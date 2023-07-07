package lam.projectend.model.service.user.message;

import lam.projectend.model.entity.message.Message;
import lam.projectend.model.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMessageService extends IService<Message,Long> {
    Page<Message> findMessageByUsersSentIdAndUsersReceivedIdOrderByDateSendDesc(Long idUsersSent, Long idUsersReceivedId, Pageable pageable);

}
