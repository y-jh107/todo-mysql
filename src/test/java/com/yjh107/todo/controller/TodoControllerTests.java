package com.yjh107.todo.controller;

import com.yjh107.todo.dto.TodoRequestDto;
import com.yjh107.todo.dto.TodoResponseDto;
import com.yjh107.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void testGetTodoById() throws Exception {
        TodoResponseDto todo = new TodoResponseDto(1L, "TestTodo", "Description", false);

        given(todoService.findById(1L)).willReturn(todo);

        mockMvc.perform(get("/api/todos/v2/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Todo"));
    }

    @Test
    public void testGetAllTodos() throws Exception {
        given(todoService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/todos/v2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        given(todoService.findAll())
                .willReturn(
                    Collections.singletonList(
                            new TodoResponseDto(1L, "Test Todo", "Description", false)));

        mockMvc.perform(get("/api/todos/v2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Todo"));
    }

    @Test
    public void testCreateTodo() throws Exception {
        TodoResponseDto todo = new TodoResponseDto(1L, "New Todo", "Description", false);
        todo.setId(1L);
        todo.setTitle("New Todo");

        given(todoService.save(any(TodoRequestDto.class))).willReturn(todo);

        mockMvc.perform(
                post("/api/todos/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Todo\", \"Description\":"+" \"Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("New Todo"));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        TodoResponseDto existingTodo = new TodoResponseDto(1L, "Existing", "Description", false);

        TodoResponseDto updatedTodo = new TodoResponseDto(1L, "Updated Todo", "Updated Description", true);

        given(todoService.findById(1L)).willReturn(existingTodo);
        given(todoService.update(anyLong(), any(TodoRequestDto.class))).willReturn(updatedTodo);

        mockMvc.perform(
                put("/api/todos/v2/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Todo\", \"description\": \"Updated" + "Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Todo"));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        TodoResponseDto todo = new TodoResponseDto(1L, "Test Todo", "Description", false);

        given(todoService.findById(1L)).willReturn(todo);

        mockMvc.perform(
                delete("/api/todos/v2/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
