package com.study.todo.model.persistence;

import com.study.todo.model.vo.TodoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapper {
    List<TodoVo> findAll();
    TodoVo findById(int id);
    List<TodoVo> findByUserId(String userId);
    int save(TodoVo vo);
    int update(TodoVo vo);
    int delete(int id);
}
