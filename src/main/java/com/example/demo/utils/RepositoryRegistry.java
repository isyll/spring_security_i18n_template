package com.example.demo.utils;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
public class RepositoryRegistry {

  private final Repositories repositories;

  public RepositoryRegistry(WebApplicationContext appContext) {
    repositories = new Repositories(appContext);
  }

  @SuppressWarnings("unchecked")
  public <T> CrudRepository<T, ?> getRepositoryEntityType(Class<T> entityClass) {
    Optional<Object> repo = repositories.getRepositoryFor(entityClass);
    if (repo.isPresent() && repo.get() instanceof CrudRepository<?, ?> crudRepo) {
      return (CrudRepository<T, ?>) crudRepo;
    }
    throw new IllegalArgumentException("No repository found for class: " + entityClass);
  }
}
