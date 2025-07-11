package com.example.demo.repository;

import com.example.demo.model.Classroom;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends PagingAndSortingRepository<Classroom, Long> {
  <S> S save(S entity);
}
