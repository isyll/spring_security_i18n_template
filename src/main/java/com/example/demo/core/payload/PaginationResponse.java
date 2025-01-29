package com.example.demo.core.payload;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PaginationResponse<T> {

    private Stream<T> content;
    @JsonProperty("total_elements")
    private long totalElements;
    @JsonProperty("total_pages")
    private long totalPages;
    @JsonProperty("has_next")
    private boolean hasNext;
    @JsonProperty("page_number")
    private long pageNumber;
    @JsonProperty("page_size")
    private long pageSize;
}
