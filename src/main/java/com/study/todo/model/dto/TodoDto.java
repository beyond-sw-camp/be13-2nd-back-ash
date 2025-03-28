package com.study.todo.model.dto;

import com.study.todo.model.vo.TodoVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
public class TodoDto {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;

    @Schema(description = "사용자아이디")
    @NotBlank
    private String userId;

    @Schema(description = "할일제목")
    @NotBlank
    private String title;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private boolean done;

    public TodoVo changeVo(){
        return TodoVo.builder()
                .id(id)
                .userId(userId)
                .title(title)
                .done(done)
                .build();
    }
}

