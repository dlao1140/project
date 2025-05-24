package com.example.board_back.service;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;

import com.example.board_back.dto.request.board.PatchBoardRequestDto;
import com.example.board_back.dto.request.board.PostBoardRequestDto;
import com.example.board_back.dto.request.board.PostCommentRequestDto;
import com.example.board_back.dto.response.board.GetBoardResponseDto;
import com.example.board_back.dto.response.board.GetFavoriteListResponseDto;
import com.example.board_back.dto.response.board.PostBoardResponseDto;
import com.example.board_back.dto.response.board.PutFavoriteResponseDto;
import com.example.board_back.dto.response.board.PostCommentResponseDto;
import com.example.board_back.dto.response.board.GetCommentListResponseDto;
import com.example.board_back.dto.response.board.IncreaseViewCountResponseDto;
import com.example.board_back.dto.response.board.DeleteBoardResponseDto;
import com.example.board_back.dto.response.board.PatchBoardResponseDto;
import com.example.board_back.dto.response.board.GetLatestBoardListResponseDto;
import com.example.board_back.dto.response.board.GetTop3BoardListResponseDto;
import com.example.board_back.dto.response.board.GetSearchBoardListResponseDto;
import com.example.board_back.dto.response.board.GetUserBoardListResponseDto;


public interface BoardService {
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);
    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boaInteger);
    ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList();
    ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList();
    ResponseEntity<? super GetSearchBoardListResponseDto>  getSearchBoardList(String searchWord, String preSearchWord);
    ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String email);
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);
    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);
    ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email);
    ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber);
    ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email);
    
}
