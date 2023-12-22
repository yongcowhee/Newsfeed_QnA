package com.sparta.newsfeed_qna.dto;

import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageDto {

    @Positive// 0보다 큰수
    private Integer currentPage;
    private Integer size;
    private String sortBy;
    private String orderBy;

    public Pageable toPageable() {
        if (orderBy.equals("desc")) {
            return PageRequest.of(currentPage - 1, size, Sort.by(sortBy).descending());
        }
        return PageRequest.of(currentPage - 1, size, Sort.by(sortBy).ascending());
    }
}