package com.yjh107.todo.repository;

import com.yjh107.todo.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Todo 항목을 저장, 조회, 수정, 삭제하는 CRUD(Create, Read, Update, Delete)
 * AtomicLong을 사용하여 Todo 항목의 고유 ID를 자동으로 생성
 * Repository  애너테이션은 자동으로 해당 클래스를 리포지토리 빈으로 관리하도록 하여
 * 다른 빈(예: 서비스 빈)이 이 리포지토리 빈을 사용할 수 있도록 만듭니다.
 */
@Repository
public class TodoInMemoryRepository {

    private final Map<Long, Todo> todoMap = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public List<Todo> findAll() {
        return new ArrayList<>(todoMap.values());
    }

    public Todo findById(Long id) {
        return todoMap.get(id);
    }

    public Todo save(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(counter.incrementAndGet());
        }

        todoMap.put(todo.getId(), todo);
        return todo;
    }

    public void deleteById(Long id) {
        todoMap.remove(id);
    }
}
