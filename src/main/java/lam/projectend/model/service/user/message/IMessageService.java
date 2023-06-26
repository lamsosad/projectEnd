package lam.projectend.model.service.user.message;

import lam.projectend.model.entity.message.Message;
import lam.projectend.model.service.IService;

import java.util.List;

public interface IMessageService extends IService<Message,Long> {
    List<Message> findMessageByUsersSentIdAndUsersReceivedIdOrderByDateSendDesc(Long idUsersSent, Long idUsersReceivedId);

}
