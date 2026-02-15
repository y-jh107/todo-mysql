package com.yjh107.todo.controller;

import com.yjh107.todo.dto.TodoRequestDto;
import com.yjh107.todo.dto.TodoResponseDto;
import com.yjh107.todo.entity.Todo;
import com.yjh107.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos/v2")
public class TodoController {

    @Autowired
    private TodoService todoService;

    // getAllTodos(): 모든 Todo 항목 조회 API
    @GetMapping
    @Operation(summary = "전체 작업 조회", description = "전체 작업 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "내용 없음")
    })
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        List<TodoResponseDto> todos = todoService.findAll();

        if (todos == null || todos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(todos);
    }

    // getTodoById(): 특정 ID의 Todo 항목 조회
    @GetMapping("/{id}")
    @Operation(summary = "작업 조회", description = "ID로 작업 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "작업 없음")
    })
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable Long id) {
        TodoResponseDto todo = todoService.findById(id);

        if (todo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(todo);
    }

    // createTodo(): Todo 항목 생성
    @PostMapping
    @Operation(summary = "작업 생성", description = "새로운 작업 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성됨")
    })
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto todo) {
        return ResponseEntity.status(201).body(todoService.save(todo));
    }

    // updateTodo: 기존 Todo 항목 수정
    @PutMapping("/{id}")
    @Operation(summary = "작업 수정", description = "ID로 작업 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "작업 없음")
    })
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id,
                                           @RequestBody TodoRequestDto todo) {
        TodoResponseDto existingTodo = todoService.findById(id);
        if (existingTodo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(todoService.update(id, todo));
    }

    // deleteTodo(): 특정 ID의 Todo 항목 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "작업 삭제", description = "ID로 작업 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "내용 없음"),
            @ApiResponse(responseCode = "404", description = "작업 없음")
    })
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        TodoResponseDto todo = todoService.findById(id);

        if (todo == null) {
            return ResponseEntity.notFound().build();
        }

        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
