package com.yjh107.todo.util;

import com.yjh107.todo.dto.TodoRequestDto;
import com.yjh107.todo.dto.TodoResponseDto;
import com.yjh107.todo.entity.Todo;

public class EntityDtoMapper {

    public static Todo toEntity(TodoRequestDto dto) {
        return new Todo(
                null, // 자동 생성 필드
                dto.getTitle(),
                dto.getDescription(),
                dto.isCompleted(),
                null // 자동 생성 필드
        );
    }

    public static TodoResponseDto toDto(Todo entity) {
        return new TodoResponseDto(
                entity.getId(), entity.getTitle(), entity.getDescription(), entity.isCompleted()
        );
    }
}
