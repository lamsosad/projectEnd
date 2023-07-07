package lam.projectend.controller;

import lam.projectend.model.dto.request.MessageDTO;
import lam.projectend.model.dto.response.ResponseMessage;
import lam.projectend.model.entity.message.Message;
import lam.projectend.model.service.user.message.IMessageService;
import lam.projectend.model.service.user.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor

public class MessageController {
    private final IMessageService messageService;
    private final IUserService userService;

    @GetMapping("/{ID1}/{ID2}")
    public Page<?> show(@PathVariable Long ID1, @PathVariable Long ID2, Pageable pageable1) {
        Pageable pageable = PageRequest.of(pageable1.getPageNumber(), 8);
        Page<Message> messages = messageService.findMessageByUsersSentIdAndUsersReceivedIdOrderByDateSendDesc(ID1, ID2, pageable);
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
