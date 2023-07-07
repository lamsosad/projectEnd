package lam.projectend.model.service.user.message;

import lam.projectend.model.entity.message.Message;
import lam.projectend.model.repository.IMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceIMPL implements IMessageService {
    private final IMessageRepository messageRepository;

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void remove(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Page<Message> findMessageByUsersSentIdAndUsersReceivedIdOrderByDateSendDesc(Long idUsersSent, Long idUsersReceivedId, Pageable pageable) {
        return messageRepository.findMessageQuery(idUsersSent,idUsersReceivedId,pageable);
    }
}
