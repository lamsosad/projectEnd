package lam.projectend.controller;

import lam.projectend.model.dto.request.SendAddFriend;
import lam.projectend.model.dto.response.ResponseMessage;
import lam.projectend.model.entity.friend.Friend;
import lam.projectend.model.entity.page.Page;
import lam.projectend.model.service.page.page.IPageService;
import lam.projectend.model.service.user.friend.IFriendService;
import lam.projectend.model.service.user.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final IUserService userService;
    private final IFriendService friendService;
    private final IPageService pageService;

    @GetMapping("{id}")
    public List<Friend> findAll(@PathVariable Long id) {
        List<Friend> friends = friendService.findFriendByStatusAndUsersId(true,id);
        return friends;
    }
    @GetMapping("friendSent/{id}")
    public List<Friend> findFriendSent(@PathVariable Long id) {
        List<Friend> friendSent = friendService.findFriendByAlertAndUsersId(true,id);
        return friendSent;
    }
    @GetMapping("/friendPage/{id}")
    public List<Page> friendPage(@PathVariable Long id) {
        return pageService.findByUsersIdAndStatusAndRegimeOrderByDatePostDesc(id, true, true);
    }


    @PostMapping("/sendAddFriend")
    public ResponseEntity<ResponseMessage> sendAddFriend(@RequestBody SendAddFriend sendAddFriend) {
        boolean check = friendService.existsFriendByUsersIdAndFriendId(sendAddFriend.getIdUser(), sendAddFriend.getIdFriend());
        if (!check) {
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String dateNow = simpleDateFormat.format(new Date());
            Friend friend = friendService.save(Friend
                    .builder()
                    .alert(true)
                    .friend(userService.findById(sendAddFriend.getIdFriend()).get())
                    .users(userService.findById(sendAddFriend.getIdUser()).get())
                    .dateAdd(dateNow)
                    .status(false)
                    .build());
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("OK")
                            .message("Đã gửi lời mời kết bạn!")
                            .data(friend)
                            .build()
            );
        } else {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Chờ xác nhận!")
                            .data("")
                            .build()
            );
        }
    }

    @PutMapping("/acceptFriend")
    public ResponseEntity<ResponseMessage> acceptFriend(@RequestBody SendAddFriend sendAddFriend) {
        Friend friend = friendService.findFriendByUsersIdAndFriendId(sendAddFriend.getIdUser(), sendAddFriend.getIdFriend());
        if (friend == null) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Đã trở thành bạn bè!")
                            .data("")
                            .build()
            );
        }
        friend.setStatus(true);
        friend.setAlert(false);
        friendService.save(friend);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Đã thêm mới bạn bè!")
                        .data(friend)
                        .build()
        );
    }

    @DeleteMapping("/deleteFriend/{id}")
    public ResponseEntity<ResponseMessage> deleteFriend(@PathVariable Long id) {
            friendService.remove(id);
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("OK")
                            .message("Đã hủy kết bạn!")
                            .data("")
                            .build()
            );
    }
}
