package com.example.board_back.service;

import org.springframework.http.ResponseEntity;

import com.example.board_back.dto.request.auth.SignUpRequestDto;
import com.example.board_back.dto.response.auth.SignUpResponseDto;
import com.example.board_back.dto.request.auth.SignInRequestDto;
import com.example.board_back.dto.response.auth.SignInResponseDto;

public interface AuthService {
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
