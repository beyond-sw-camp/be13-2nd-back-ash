package com.study.todo.controller;

import com.study.todo.model.dto.ResponseDto;
import com.study.todo.model.dto.TodoDto;
import com.study.todo.model.service.TodoService;
import com.study.todo.model.vo.TodoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@Tag(name = "Todo APIs", description = "오늘 할일 API 목록")
public class TodoController {

    @Autowired
    private TodoService service;

    
    @Operation(summary = "할일정보 삭제", description = "일정정보 id를 받아 수정한다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZIED",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseDto<TodoDto>> delete(@PathVariable int id){
        List<TodoDto> listDto = service.delete(id);
        ResponseDto<TodoDto> res = ResponseDto.<TodoDto>builder()
                .data(listDto)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }



    @Operation(summary = "할일정보 수정", description = "일정정보를 JSON으로 받아 수정한다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZIED",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    @PutMapping
    public ResponseEntity<ResponseDto<TodoDto>> update(@RequestBody TodoDto dto){
        List<TodoDto> listDto = service.update(dto);
        ResponseDto<TodoDto> res = ResponseDto.<TodoDto>builder()
                .data(listDto)
                .build();

        return ResponseEntity.ok().body(res);
    }


    @Operation(summary = "할일목록 출력", description = "일정 정보를 JSON으로 출력한다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Request Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "NOT FOUND(페이지없음)", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR(서버내부오류)", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<TodoDto>> getTodoList(@PathVariable String userId) {
        List<TodoDto> listDto = service.findByUserId(userId);
        ResponseDto<TodoDto> res = ResponseDto.<TodoDto>builder()
                .data(listDto)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @Operation(summary = "★ 할일등록", description = "일정 정보를 JSON으로 받아서 등록한다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZIED",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })


    @PostMapping
    public ResponseEntity<ResponseDto<TodoDto>> createTodo(
            @Parameter(required = true, description = "투두 커맨드객체")
            @RequestBody TodoDto dto, BindingResult result
    ) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<TodoDto> listDto = service.save(dto);

        ResponseDto<TodoDto> res = ResponseDto.<TodoDto>builder()
                .data(listDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
