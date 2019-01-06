package com.taskstorage.wireframe.repository;

import com.taskstorage.wireframe.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
    List<Task> findByDescriptionContainingOrContentContaining(String descTag, String contentTag);
}
