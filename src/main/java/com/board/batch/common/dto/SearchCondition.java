package com.board.batch.common.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCondition {

    private String keyword;
    private String sort;

    private Integer page;           // 현재페이지 (0-base)
    private Integer size;           // 페이지 크기
    private int totalElements;      // 전체 데이터 수
    private int totalPages;         // 전체 페이지 수


    public void init(int totalElements) {
        // default 처리
        if (page == null || page < 0) page = 0;
        if (size == null || size <= 0) size = 10;

        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
    }

}
