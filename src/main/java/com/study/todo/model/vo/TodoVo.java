package com.study.todo.model.vo;

import com.study.todo.model.dto.TodoDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class TodoVo implements Serializable {
    private Integer id;

    @NotBlank
    private String userId;

    @NotBlank
    private String title;
    private boolean done;

    public TodoDto changeDto(){
        return TodoDto.builder()
                .id(id)
                .userId(userId)
                .title(title)
                .done(done)
                .build();
    }

}
