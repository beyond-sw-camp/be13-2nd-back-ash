package com.study.todo.model.service;

import com.study.todo.model.dto.TodoDto;
import com.study.todo.model.vo.TodoVo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TodoService {
    List<TodoDto> findAll();
    TodoDto findById(int id);
    List<TodoDto> findByUserId(String userId);
    List<TodoDto> save(TodoDto dto);
    List<TodoDto> update(TodoDto dto);
    List<TodoDto> delete(int id);
}
