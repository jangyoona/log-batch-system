package com.board.batch.user.service.impl;

import com.board.batch.common.dto.SearchCondition;
import com.board.batch.user.dto.PostAttachments;
import com.board.batch.user.dto.PostDto;
import com.board.batch.user.mapper.PostMapper;
import com.board.batch.user.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;


    public PostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public List<PostDto> getPosts(SearchCondition searchReq) {
        int count = postMapper.getPostCount(searchReq);
        searchReq.init(count);

        return postMapper.getPosts(searchReq);
    }

    @Override
    public PostDto getPostById(Long id) {
        return postMapper.getPostById(id);
    }

    @Override
    @Transactional
    public long insertPost(PostDto postDto, MultipartFile[] attachs) {

        int rows = postMapper.insertPost(postDto);

        long postId = 0;
        if(rows > 0) {
            postId = postDto.getId();
        } else {
            return 0;
        }

        if(attachs != null && attachs.length > 0){
            // 첨부파일이 존재 시
            for(MultipartFile attach : attachs){
                if(!attach.isEmpty()){

                    String storedName = "";
                    try {
                        File dir = new File(uploadDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }

                        storedName = UUID.randomUUID().toString();
                        attach.transferTo(new File(uploadDir + storedName));

                        PostAttachments postAttachment = new PostAttachments();
                        postAttachment.setPostId(postId);
                        postAttachment.setOriginName(attach.getOriginalFilename());
                        postAttachment.setStoredName(storedName);

                        int attachRows = postMapper.insertPostAttachments(postAttachment);
                    } catch (Exception e) {
                        if(!storedName.isEmpty()){
                            File file = new File(uploadDir + storedName);
                            file.delete();
                        }
                        log.error("파일 업로드 중 오류 발생 | fileName({}): {}", storedName, e);
                    }
                }
            }
        }

        return postId;
    }

    @Override
    public int editPost(PostDto postDto, MultipartFile[] attachs) {
        int rows = postMapper.updatePost(postDto);
        // 나중에 파일 삭제 및 업로드 추가 예정
        return rows;
    }

    @Override
    public int deletePost(long id) {
        int rows = postMapper.deletePost(id);
        // 나중에 파일 삭제 및 업로드 추가 예정
        return rows;
    }
}
