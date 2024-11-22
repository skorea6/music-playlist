package com.example.musicplaylist.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.min;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageWrapper<T> {
    private List<T> content;
    private int totalPages; // 전체 페이지 수
    private long totalElements; // 전체 데이터 수
    private int pageElements; // 현재 페이지 데이터 수
    private int size; // 한 페이지 최대 데이터 수
    private int page; // 현재 페이지 번호
    private int pageBlockCount; // 블럭의 첫번째 페이지 번호
    private int firstBlockPage; // 블럭의 첫번째 페이지 번호
    private int lastBlockPage; // 블럭의 마지막 페이지 번호

    public static <T> PageWrapper<T> fromPage(Page<T> page, int pageBlockCount) {
        int pageNum = page.getNumber() + 1;
        int block = (int) ceil(pageNum / (double) pageBlockCount);

        int firstBlockPage = ((block - 1) * pageBlockCount) + 1;
        int lastBlockPage = min(page.getTotalPages(), block * pageBlockCount);

        return new PageWrapper<>(
                page.getContent(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumberOfElements(),
                page.getSize(),
                pageNum,
                pageBlockCount,
                firstBlockPage,
                lastBlockPage
        );
    }
}
