package com.example.board_back.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.board_back.common.ResponseCode;
import com.example.board_back.common.ResponseMessage;
import com.example.board_back.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class PostBoardResponseDto  extends ResponseDto{
    
    private PostBoardResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<PostBoardResponseDto> success() {
        PostBoardResponseDto result = new PostBoardResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

}
