package com.study.todo.model.service;

import com.study.todo.model.dto.TodoDto;
import com.study.todo.model.persistence.TodoMapper;
import com.study.todo.model.vo.TodoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoMapper mapper;

    @Override
    public List<TodoDto> findAll() {
        return mapper.findAll().stream().map(vo->vo.changeDto()).collect(Collectors.toList());
    }

    @Override
    public TodoDto findById(int id) {
        return mapper.findById(id).changeDto();
    }

    @Override
    public List<TodoDto> findByUserId(String userId) {
        return mapper.findByUserId(userId).stream().map(vo->vo.changeDto()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TodoDto> save(TodoDto dto) {
        if(dto==null) {
            log.info("TodoDto cannot be null~!");
            throw new IllegalArgumentException("TodoDto cannot be null~!");
        }

        if(dto.getUserId()==null) {
            log.info("Unknown User");
            throw new IllegalArgumentException("Unknown User");
        }

        int rows = mapper.save(dto.changeVo());

        if (rows < 1) {
            throw new IllegalArgumentException("데이터 삭제에 실패하였습니다.");
        }

        return mapper.findAll().stream().map(vo->vo.changeDto()).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public List<TodoDto> update(TodoDto dto) {
        //1. 저장할 dto가 유효한지 확인
        TodoVo vo = mapper.findById(dto.getId());
        if(vo==null) {
            throw new IllegalArgumentException("업데이트할 데이터가 존재하지 않습니다.");
        }
        
        //2. 반환된 vo가 존재하면 새 dto 값으로 변경
        vo.setTitle(dto.getTitle());
        vo.setDone(dto.isDone());

        log.info("★★★★★ vo = "+vo);

        //3. DB에 업데이트
        mapper.update(vo);

        return findByUserId(vo.getUserId());
    }


    @Override
    @Transactional
    public List<TodoDto> delete(int id) {
        //1. 삭제할 vo 객체가 존재하는지 확인
        TodoVo vo = mapper.findById(id);
        if(vo==null) {
            throw new IllegalArgumentException("업데이트할 데이터가 존재하지 않습니다.");
        }
        
        //2. 해당vo 삭제
        mapper.delete(id);
        
        //3. 리스트 리턴
        return mapper.findByUserId(vo.getUserId()).stream().map(v->v.changeDto()).collect(Collectors.toList());
    }
}


































