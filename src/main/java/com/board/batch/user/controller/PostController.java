package com.board.batch.user.controller;


import com.board.batch.user.dto.PostDto;
import com.board.batch.user.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    /**
     * 게시판 리스트
     */
    @GetMapping
    public String listPosts(Model model) {

        List<PostDto> posts = postService.getPosts();

        // pageVo 만들기
        model.addAttribute("page", 1);
        model.addAttribute("totalPages", 10);
        model.addAttribute("totalElements", 1);

        model.addAttribute("posts",posts);
        return "posts/list";
    }

    /**
     * 게시판 상세
     */
    @GetMapping("{id}")
    public Model detailPost(Model model, Long id) {

        model.addAttribute("post","게시글 상세");
        return model;
    }

    /**
     * 게시글 작성 폼
     */
    @GetMapping("/new")
    public String createPostForm(Model model) {

        return "posts/posts-new";
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/new")
    public ResponseEntity<HttpStatusCode> createPost(HttpSession session, PostDto post, MultipartFile[] attachs) {

        Object userName = session.getAttribute("userName");

        if(userName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        post.setUserName((String) userName);

        int result = postService.insertPost(post, attachs);

        if(result > 0){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 게시글 수정 폼
     */
    @GetMapping("/{id}/edit")
    public Model editPostForm(Model model, Long id) {

        model.addAttribute("post","게시글 수정폼 반환");
        return model;
    }

    /**
     * 게시글 수정
     */
    @PostMapping("/{id}/edit")
    public Model updatePost(Model model, Long id) {

        model.addAttribute("post","게시글 수정");
        return model;
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}/delete")
    public Model deletePost(Model model, Long id) {

        model.addAttribute("post","게시글 수정");
        return model;
//        return "redirect:/posts";
    }






}
