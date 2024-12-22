package com.isyll.demo_app.dto.payload.response;

import java.util.stream.Stream;

import lombok.Data;

@Data
public class PaginationResponse<T> {

    private Stream<T> content;
    private long totalElements;
    private long totalPages;
    private boolean hasNext;
    private long pageNumber;
    private long pageSize;
}
