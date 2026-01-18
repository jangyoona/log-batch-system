package com.board.batch.user.controller;


import com.board.batch.common.dto.SearchCondition;
import com.board.batch.user.dto.PostDto;
import com.board.batch.user.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;


    /**
     * 게시판 리스트
     */
    @GetMapping
    public String listPosts(Model model, SearchCondition searchReq) {

        try {
            List<PostDto> posts = postService.getPosts(searchReq);

            model.addAttribute("searchReq", searchReq);
            model.addAttribute("posts", posts);
            return "posts/list";
        } catch (Exception e) {
            log.error("게시글 리스트 조회 중 오류 발생: ", e);
            return "error";
        }
    }

    /**
     * 게시판 상세
     */
    @GetMapping("/{id}")
    public String detailPost(Model model, @PathVariable("id") Long id) {

        try {
            PostDto post = postService.getPostById(id);
            model.addAttribute("post", post);

            return "posts/view";

        } catch (Exception e) {
            log.error("게시글 상세 조회 중 오류 발생: ", e);
            return "error";
        }
    }

    /**
     * 게시글 작성 폼
     */
    @GetMapping("/new")
    public String createPostForm() {

        return "posts/form";
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/new")
    public ResponseEntity<Object> createPost(HttpSession session, @RequestBody PostDto post, MultipartFile[] attachs) {

        try {
            Object userName = session.getAttribute("userName");

            if(userName == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            post.setUserName((String) userName);

            long result = postService.insertPost(post, attachs);

            if(result > 0) {
                return ResponseEntity.status(200).body(result);
            } else {
                return ResponseEntity.status(400).body("게시글 작성 실패했습니다.\n새로고침 후 다시 시도해 주세요.");
            }

        } catch (Exception e) {
            log.error("게시글 작성 중 오류 발생: ", e);
            return ResponseEntity.status(500).body("문제가 발생했습니다.\n새로고침 후 다시 시도해 주세요.");
        }
    }

    /**
     * 게시글 수정 폼
     */
    @GetMapping("/{id}/edit")
    public String editPostForm(Model model, @PathVariable("id") Long id, String mode) {

        PostDto post = postService.getPostById(id);
        model.addAttribute("post",post);
        model.addAttribute("mode",mode);

        return "posts/form";
    }

    /**
     * 게시글 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity<Object> updatePost(@PathVariable("id") Long id, @RequestBody PostDto post, MultipartFile[] attachs, HttpSession session) {


        try {
            if(id == null || id == 0){
                return ResponseEntity.status(400).body("게시글 수정 실패했습니다.\n새로고침 후 다시 시도해 주세요.");
            }

            Object userName = session.getAttribute("userName");
            if (userName == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            post.setId(id);
            post.setUserName((String) userName);

            int result = postService.editPost(post, attachs);

            if (result > 0) {
                return ResponseEntity.status(200).body(post.getId());
            } else {
                return ResponseEntity.status(400).body("게시글 수정 실패했습니다.\n새로고침 후 다시 시도해 주세요.");
            }
        } catch (Exception e) {
            log.error("게시글 수정 중 오류 발생: ", e);
            return ResponseEntity.status(500).body("문제가 발생했습니다.\n새로고침 후 다시 시도해 주세요.");
        }
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deletePost(Long id) {

        try {

            if(id == null || id <= 0){
                return ResponseEntity.status(400).body("게시글 삭제 실패했습니다.\n새로고침 후 다시 시도해 주세요.");
            }

            int result = postService.deletePost(id);

            if (result > 0) {
                return  ResponseEntity.status(200).build();
            } else {
                return ResponseEntity.status(400).body("게시글 삭제 실패했습니다.\n새로고침 후 다시 시도해 주세요.");
            }

        } catch (Exception e) {
            log.error("게시글 삭제 중 오류 발생: ", e);
            return ResponseEntity.status(500).body("문제가 발생했습니다.\n새로고침 후 다시 시도해 주세요.");
        }
    }






}
