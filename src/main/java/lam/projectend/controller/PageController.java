package lam.projectend.controller;

import lam.projectend.model.dto.request.CommentDTO;
import lam.projectend.model.dto.request.LikeDTO;
import lam.projectend.model.dto.request.PageUp;
import lam.projectend.model.dto.response.ResponseMessage;
import lam.projectend.model.entity.page.Comment;
import lam.projectend.model.entity.page.Like;
import lam.projectend.model.entity.page.Page;
import lam.projectend.model.entity.user.Users;
import lam.projectend.model.security.userPrincipal.UserDetailService;
import lam.projectend.model.service.page.cmt.ICommentService;
import lam.projectend.model.service.page.like.ILikeService;
import lam.projectend.model.service.page.page.IPageService;
import lam.projectend.model.service.user.friend.IFriendService;
import lam.projectend.model.service.user.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/page")
@RequiredArgsConstructor

public class PageController {
    private final UserDetailService userDetailService;
    private final ILikeService likeService;
    private final IPageService pageService;
    private final IUserService userService;
    private final ICommentService commentService;
    private final IFriendService friendService;

    @GetMapping
    public List<Page> showAllPage() {
        List<Page> pages = pageService.findPageByStatusAndRegimeOrderByDatePostDesc(true, true);
        return pages;
    }

    @GetMapping("{id}")
    public ResponseEntity<Page> findPageById(@PathVariable Long id) {
        Optional<Page> page = pageService.findById(id);
        if (!page.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(page.get(), HttpStatus.OK);
    }

    @GetMapping("/myPage/{id}")
    public List<Page> myPage(@PathVariable Long id) {
        return pageService.findByUsersIdAndStatusOrderByDatePostDesc(id, true);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseMessage> searchProduct(@RequestParam String search) {
        List<Page> page = pageService.searchByTitle(search);
        if (page.isEmpty()) {
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Không tìm thấy bài viết!")
                            .data("")
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("")
                        .data(page)
                        .build()
        );
    }

    @GetMapping("/comment/{id}")
    public List<Comment> findAllByPage(@PathVariable Long id) {
        return commentService.findCommentByPageId(id);
    }

    @PostMapping("/comment")
    public ResponseEntity<ResponseMessage> commentPage(@RequestBody CommentDTO commentDTO) {
        Optional<Page> pageOptional = pageService.findById(commentDTO.getIdPage());
        Optional<Users> usersOptional = userService.findById(commentDTO.getIdUser());
        if (!pageOptional.isPresent()||!usersOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentService.save(Comment
                .builder()
                .content(commentDTO.getContent())
                .page(pageOptional.get())
                .user(usersOptional.get())
                .build()
        );

        pageOptional.get().setCountCmt(commentService.countCommentByPageId(commentDTO.getIdPage()));
        pageService.save(pageOptional.get());
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Đã Comment")
                        .data(commentDTO)
                        .build()
        );
    }

    @DeleteMapping("/deleteCmt")
    public ResponseEntity<ResponseMessage> deleteCmt(@RequestBody CommentDTO commentDTO) {
        commentService.deleteCommentByIdAndUserIdAndPageId(commentDTO.getId(), commentDTO.getIdUser(), commentDTO.getIdPage());
        Page page = pageService.findById(commentDTO.getIdPage()).get();
        if (page == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        page.setCountCmt(commentService.countCommentByPageId(commentDTO.getIdPage()));
        pageService.save(page);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("Failed")
                        .message("Đã xóa comment!")
                        .data("")
                        .build()
        );

    }

    @PostMapping("/like")
    public ResponseEntity<ResponseMessage> likePage(@RequestBody LikeDTO likeDTO) {
        boolean check = likeService.existsByUserIdAndPageId(likeDTO.getIdUser(), likeDTO.getIdPage());
        if (check) {
            likeService.deleteLikeByUserIdAndPageId(likeDTO.getIdUser(), likeDTO.getIdPage());
            Page page = pageService.findById(likeDTO.getIdPage()).get();
            if (page == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            page.setCountLike(likeService.countLikeByPageId(likeDTO.getIdPage()));
            pageService.save(page);
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Đã unLike!")
                            .data("")
                            .build()
            );
        } else {
            Like like = likeService.save(Like
                    .builder()
                    .page(pageService.findById(likeDTO.getIdPage()).get())
                    .user(userService.findById(likeDTO.getIdUser()).get())
                    .status(true)
                    .build()
            );
            Page page = pageService.findById(likeDTO.getIdPage()).get();
            if (page == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            page.setCountLike(likeService.countLikeByPageId(likeDTO.getIdPage()));
            pageService.save(page);
            return ResponseEntity.ok().body(
                    ResponseMessage.builder()
                            .status("OK")
                            .message("Đã like")
                            .data(like)
                            .build()
            );

        }
    }

    @GetMapping("/delete/{idDel}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable Long idDel) {
        Page page = pageService.findById(idDel).get();
        if (page == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        page.setStatus(false);
        pageService.save(page);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Đã xóa bài viết!")
                        .data("")
                        .build()
        );
    }

//    @PutMapping("/changeRegime/{id}")
//    public ResponseEntity<ResponseMessage> changeRegime(@PathVariable Long id) {
//        Page page = pageService.findById(id).get();
//        if (page == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        page.setRegime(!page.isRegime());
//        pageService.save(page);
//        return ResponseEntity.ok().body(
//                ResponseMessage.builder()
//                        .status("OK")
//                        .message("Đã thay đổi chế độ bài viết!")
//                        .data("")
//                        .build()
//        );
//
//    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> create(@RequestBody PageUp pages) {
        Users users = userDetailService.getUsersPrincipal();
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateNow = simpleDateFormat.format(new Date());
        Page page = Page.builder()
                .title(pages.getTitle())
                .image(pages.getImageUrls())
                .datePost(dateNow)
                .status(true)
                .regime(pages.isRegime())
                .countLike(null)
                .countCmt(null)
                .users(users)
                .build();
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Thêm bài viết thành công")
                        .data(pageService.save(page))
                        .build()
        );
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseMessage> editTitle(@PathVariable Long id, @RequestBody PageUp pageUp) {
        Page page = pageService.findById(id).get();
        if (page == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        page.setTitle(pageUp.getTitle());
        page.setRegime(pageUp.isRegime());
        Page updatepage = pageService.save(page);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Đã sửa đổi bài viết!")
                        .data(updatepage)
                        .build()
        );
    }

}
