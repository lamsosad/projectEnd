package lam.projectend.controller;

import lam.projectend.model.dto.request.MessageDTO;
import lam.projectend.model.dto.response.ResponseMessage;
import lam.projectend.model.entity.message.Message;
import lam.projectend.model.service.user.message.IMessageService;
import lam.projectend.model.service.user.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor

public class MessageController {
    private final IMessageService messageService;
    private final IUserService userService;

    @GetMapping
    public List<Message> show(@RequestBody MessageDTO messageDTO) {
        List<Message> messages = messageService.findMessageByUsersSentIdAndUsersReceivedIdOrderByDateSendDesc(messageDTO.getIdSent(), messageDTO.getIdReceiver());
        List<Message> messages1 = messageService.findMessageByUsersSentIdAndUsersReceivedIdOrderByDateSendDesc(messageDTO.getIdReceiver(), messageDTO.getIdSent());
        messages.addAll(messages1);
        return messages;
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> sendMessage(@RequestBody MessageDTO messageDTO) {
        String pattern = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateNow = simpleDateFormat.format(new Date());
        String mess = messageDTO.getMessage();
        if (mess == "" || mess == null) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Không để trống nhé!")
                            .data("")
                            .build()
            );
        }
        messageService.save(Message.builder()
                .dateSend(dateNow)
                .message(mess)
                .usersSent(userService.findById(messageDTO.getIdSent()).get())
                .usersReceived(userService.findById(messageDTO.getIdReceiver()).get())
                .build());
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Đã gửi tin nhắn!")
                        .data(messageDTO)
                        .build()
        );


    }
}
