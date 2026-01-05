package com.board.batch.user.controller;


import com.board.batch.user.dto.PostDto;
import com.board.batch.user.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        model.addAttribute("posts",posts);
        return "posts";
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
    public Model createPostForm(Model model) {

        model.addAttribute("post","게시글 등록");
        return model;
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/new")
    public Model createPost(Model model) {

        model.addAttribute("post","게시글 등록");
        return model;
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
