package com.example.demo.utils;

import com.example.demo.dto.response.common.PaginatedResponse;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactivePaginationUtils {

  /**
   * Paginate reactive data from a Flux source and total count Mono.
   *
   * @param dataFlux Element flow
   * @param totalCountMono Mono with total elements
   * @param page page number (0-based)
   * @param size page size
   * @param <T> element type
   * @return PaginatedResponse<T> Mono
   */
  public static <T> Mono<PaginatedResponse<T>> paginate(
      Flux<T> dataFlux, Mono<Long> totalCountMono, int page, int size) {

    return totalCountMono
        .zipWith(dataFlux.skip((long) page * size).take(size).collectList())
        .map(
            tuple -> {
              long total = tuple.getT1();
              List<T> dataList = tuple.getT2();
              return new PaginatedResponse<>(dataList, page, size, total);
            });
  }
}
