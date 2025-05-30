package com.example.board_back.service.implement;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.board_back.dto.response.board.DeleteCommentResponseDto;
import com.example.board_back.dto.response.board.BoardListItemResponseDto;
import com.example.board_back.dto.request.board.PatchBoardRequestDto;
import com.example.board_back.dto.request.board.PostBoardRequestDto;
import com.example.board_back.dto.request.board.PostCommentRequestDto;
import com.example.board_back.dto.response.ResponseDto;
import com.example.board_back.dto.response.board.DeleteBoardResponseDto;
import com.example.board_back.dto.response.board.GetBoardResponseDto;
import com.example.board_back.dto.response.board.GetCommentListResponseDto;
import com.example.board_back.dto.response.board.GetFavoriteListResponseDto;
import com.example.board_back.dto.response.board.GetLatestBoardListResponseDto;
import com.example.board_back.dto.response.board.GetSearchBoardListResponseDto;
import com.example.board_back.dto.response.board.GetTop3BoardListResponseDto;
import com.example.board_back.dto.response.board.GetUserBoardListResponseDto;
import com.example.board_back.dto.response.board.IncreaseViewCountResponseDto;
import com.example.board_back.dto.response.board.PatchBoardResponseDto;
import com.example.board_back.dto.response.board.PostBoardResponseDto;
import com.example.board_back.dto.response.board.PostCommentResponseDto;
import com.example.board_back.dto.response.board.PutFavoriteResponseDto;
import com.example.board_back.entity.BoardEntity;
import com.example.board_back.entity.BoardListViewEntity;
import com.example.board_back.entity.CommentEntity;
import com.example.board_back.entity.FavoriteEntity;
import com.example.board_back.entity.ImageEntity;
import com.example.board_back.entity.SearchLogEntity;
import com.example.board_back.repository.BoardListViewRepository;
import com.example.board_back.repository.BoardRepository;
import com.example.board_back.repository.CommentRepository;
import com.example.board_back.repository.FavoriteRepository;
import com.example.board_back.repository.ImageRepository;
import com.example.board_back.repository.SearchLogRepository;
import com.example.board_back.repository.UserRespository;
import com.example.board_back.repository.resultSet.GetBoardResultSet;
import com.example.board_back.repository.resultSet.GetCommentListResultSet;
import com.example.board_back.repository.resultSet.GetFavoriteListResultSet;
import com.example.board_back.service.BoardService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService{

    private final UserRespository userRespository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;
    private final FavoriteRepository favoriteRepository;
    private final BoardListViewRepository boardListViewRepository;
    private final SearchLogRepository searchLogRepository;

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            resultSet = boardRepository.getBoard(boardNumber);
            if(resultSet == null) return GetBoardResponseDto.noExistBoard();

            imageEntities = imageRepository.findByBoardNumber(boardNumber);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }    
        return GetBoardResponseDto.success(resultSet,imageEntities);
    }

    @Override
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {

        List<GetFavoriteListResultSet> resultSets = new ArrayList<>();
        try {
            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if(!existedBoard) return GetFavoriteListResponseDto.noExistBoard();

            resultSets = favoriteRepository.getFavoriteList(boardNumber);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetFavoriteListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {

        List<GetCommentListResultSet> resultSets = new ArrayList<>();

        try {
            
            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if(!existedBoard) return GetCommentListResponseDto.noExistBoard();

            resultSets = commentRepository.getCommentList(boardNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
            // TODO: handle exception
        }
        return GetCommentListResponseDto.success(resultSets);
    }

    // @Override
    // public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {

    //     List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

    //     try {
    //         boardListViewEntities = boardListViewRepository.findByOrderByWriteDatetimeDesc();
    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         return ResponseDto.databaseError();
    //     }
    //     return GetLatestBoardListResponseDto.success(boardListViewEntities);
    // }
    @Override
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {

    
        List<BoardListItemResponseDto> boardListItemResponseDtos = new ArrayList<>(); // ✨ 변경: 반환할 DTO 리스트

        try {
            List<BoardListViewEntity> boardListViewEntities = boardListViewRepository.findByOrderByWriteDatetimeDesc();

            // ✨ 각 BoardListViewEntity에 대해 imageCount를 조회하고 BoardListItemResponseDto로 변환
            for (BoardListViewEntity boardListViewEntity : boardListViewEntities) {
                int boardNumber = boardListViewEntity.getBoardNumber();
                long imageCount = imageRepository.countByBoardNumber(boardNumber); // ImageRepository의 메소드 사용

                BoardListItemResponseDto boardListItemResponseDto = new BoardListItemResponseDto(boardListViewEntity, (int) imageCount);
                boardListItemResponseDtos.add(boardListItemResponseDto);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        // ✨ GetLatestBoardListResponseDto.success() 메소드가 List<BoardListItemResponseDto>를 받을 수 있도록
        // GetLatestBoardListResponseDto 클래스 내부도 수정 필요
        return GetLatestBoardListResponseDto.success(boardListItemResponseDtos); 
    }

    // @Override
    // public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord,
    //         String preSearchWord) {

    //     List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

    //     try {

    //         boardListViewEntities = boardListViewRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByWriteDatetimeDesc(searchWord, searchWord);
            
    //         SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord, preSearchWord, false);
    //         searchLogRepository.save(searchLogEntity);

    //         boolean relation = preSearchWord != null;
    //         if(relation) {
    //             searchLogEntity = new SearchLogEntity(preSearchWord, searchWord, true);
    //             searchLogRepository.save(searchLogEntity);
    //         }

    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         return ResponseDto.databaseError();
    //     }
    //     return GetSearchBoardListResponseDto.success(boardListViewEntities);
    // }
    @Override
    public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord,
                                                                                String preSearchWord) {

        // List<BoardListViewEntity> boardListViewEntities = new ArrayList<>(); // ✨ 기존 코드 주석 또는 삭제
        List<BoardListItemResponseDto> boardListItemResponseDtos = new ArrayList<>(); // ✨ 변경: 반환할 DTO 리스트

        try {
            List<BoardListViewEntity> boardListViewEntities = 
                boardListViewRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByWriteDatetimeDesc(searchWord, searchWord);
            
            // ✨ 각 BoardListViewEntity에 대해 imageCount를 조회하고 BoardListItemResponseDto로 변환
            for (BoardListViewEntity boardListViewEntity : boardListViewEntities) {
                int boardNumber = boardListViewEntity.getBoardNumber();
                long imageCount = imageRepository.countByBoardNumber(boardNumber);

                BoardListItemResponseDto boardListItemResponseDto = new BoardListItemResponseDto(boardListViewEntity, (int) imageCount);
                boardListItemResponseDtos.add(boardListItemResponseDto);
            }
            
            // 검색 로그 저장 로직은 그대로 유지
            SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord, preSearchWord, false);
            searchLogRepository.save(searchLogEntity);

            boolean relation = preSearchWord != null;
            if(relation) {
                searchLogEntity = new SearchLogEntity(preSearchWord, searchWord, true);
                searchLogRepository.save(searchLogEntity);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        // ✨ GetSearchBoardListResponseDto.success() 메소드가 List<BoardListItemResponseDto>를 받을 수 있도록
        // GetSearchBoardListResponseDto 클래스 내부도 수정 필요
        return GetSearchBoardListResponseDto.success(boardListItemResponseDtos);
    }

    // @Override
    // public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String email) {

    //     List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
    //     try {
            
    //         boolean existedUser = userRespository.existsByEmail(email);
    //         if(!existedUser) return GetUserBoardListResponseDto.noExistUser();

    //         boardListViewEntities = boardListViewRepository.findByWriterEmailOrderByWriteDatetimeDesc(email);

    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         return ResponseDto.databaseError();
    //     }
    //     return GetUserBoardListResponseDto.success(boardListViewEntities);
    // }
    @Override
    public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String email) {

        // List<BoardListViewEntity> boardListViewEntities = new ArrayList<>(); // ✨ 기존 코드 주석 또는 삭제
        List<BoardListItemResponseDto> boardListItemResponseDtos = new ArrayList<>(); // ✨ 변경: 반환할 DTO 리스트

        try {
            boolean existedUser = userRespository.existsByEmail(email);
            if(!existedUser) return GetUserBoardListResponseDto.noExistUser();

            List<BoardListViewEntity> boardListViewEntities = 
                boardListViewRepository.findByWriterEmailOrderByWriteDatetimeDesc(email);

            // ✨ 각 BoardListViewEntity에 대해 imageCount를 조회하고 BoardListItemResponseDto로 변환
            for (BoardListViewEntity boardListViewEntity : boardListViewEntities) {
                int boardNumber = boardListViewEntity.getBoardNumber();
                long imageCount = imageRepository.countByBoardNumber(boardNumber);

                BoardListItemResponseDto boardListItemResponseDto = new BoardListItemResponseDto(boardListViewEntity, (int) imageCount);
                boardListItemResponseDtos.add(boardListItemResponseDto);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        // ✨ GetUserBoardListResponseDto.success() 메소드가 List<BoardListItemResponseDto>를 받을 수 있도록
        // GetUserBoardListResponseDto 클래스 내부도 수정 필요
        return GetUserBoardListResponseDto.success(boardListItemResponseDtos);
    }

    // @Override
    // public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {

    //     List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
        
    //     try {
    //         Date beforeWeek = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
    //         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //         String sevenDaysAgo = simpleDateFormat.format(beforeWeek);
    //         boardListViewEntities = boardListViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(sevenDaysAgo);
    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         return ResponseDto.databaseError();
    //     }
    //     return GetTop3BoardListResponseDto.success(boardListViewEntities);
    // }
    @Override
    public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {

        // List<BoardListViewEntity> boardListViewEntities = new ArrayList<>(); // ✨ 기존 코드 주석 또는 삭제
        List<BoardListItemResponseDto> boardListItemResponseDtos = new ArrayList<>(); // ✨ 변경: 반환할 DTO 리스트
        
        try {
            Date beforeWeek = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sevenDaysAgo = simpleDateFormat.format(beforeWeek);
            
            List<BoardListViewEntity> boardListViewEntities = 
                boardListViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(sevenDaysAgo);

            // ✨ 각 BoardListViewEntity에 대해 imageCount를 조회하고 BoardListItemResponseDto로 변환
            for (BoardListViewEntity boardListViewEntity : boardListViewEntities) {
                int boardNumber = boardListViewEntity.getBoardNumber();
                long imageCount = imageRepository.countByBoardNumber(boardNumber);

                BoardListItemResponseDto boardListItemResponseDto = new BoardListItemResponseDto(boardListViewEntity, (int) imageCount);
                boardListItemResponseDtos.add(boardListItemResponseDto);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        // ✨ GetTop3BoardListResponseDto.success() 메소드가 List<BoardListItemResponseDto>를 받을 수 있도록
        // GetTop3BoardListResponseDto 클래스 내부도 수정 필요
        return GetTop3BoardListResponseDto.success(boardListItemResponseDtos);
    }

    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {
        try {

            boolean existedEmail = userRespository.existsByEmail(email);
            if(!existedEmail) return PostBoardResponseDto.noExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, email);
            boardRepository.save(boardEntity);

            int boardNumber = boardEntity.getBoardNumber();

            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for(String image: boardImageList) {
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }

            imageRepository.saveAll(imageEntities);


            
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email) {
        try {
            
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if(boardEntity == null) return PostCommentResponseDto.noExistBoard();

            boolean existedUser = userRespository.existsByEmail(email);
            if(!existedUser) return PostBoardResponseDto.noExistUser();

            CommentEntity commentEntity = new CommentEntity(dto,boardNumber,email);
            commentRepository.save(commentEntity);

            boardEntity.increaseCommentCount();
            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostCommentResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {
        try {

            boolean existedUser = userRespository.existsByEmail(email);
            if(!existedUser) return PutFavoriteResponseDto.noExistUser();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if(boardEntity == null) return PutFavoriteResponseDto.noExistBoard();
            
            FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);
            if(favoriteEntity == null) {
                favoriteEntity = new FavoriteEntity(email, boardNumber);
                favoriteRepository.save(favoriteEntity);
                boardEntity.increaseFavoriteCount();
            }
            else {
                favoriteRepository.delete(favoriteEntity);
                boardEntity.decreaseFavoriteCount();
            }

            boardRepository.save(boardEntity);

            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PutFavoriteResponseDto.success();
    }


    @Override
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber,
            String email) {
                try {
                    BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
                    if(boardEntity == null) return PatchBoardResponseDto.noExistBoard();

                    boolean existedUser = userRespository.existsByEmail(email);
                    if(!existedUser) return PatchBoardResponseDto.noExistUser();

                    String writerEmail = boardEntity.getWriterEmail();
                    boolean isWriter = writerEmail.equals(email);
                    if(!isWriter) return PatchBoardResponseDto.noPermission();

                    boardEntity.patchBoard(dto);
                    boardRepository.save(boardEntity);
                    
                    imageRepository.deleteByBoardNumber(boardNumber);
                    List<String> boardImageList = dto.getBoardImageList();
                    List<ImageEntity> imageEntities = new ArrayList<>();

                    for(String image: boardImageList){
                        ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                        imageEntities.add(imageEntity);
                    }

                    imageRepository.saveAll(imageEntities);

                } catch (Exception exception) {
                    exception.printStackTrace();
                    return ResponseDto.databaseError();
                }
            return PatchBoardResponseDto.sucess();
        }

    @Override
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber) {
        try {
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if(boardEntity == null) return IncreaseViewCountResponseDto.noExistBoard();

            boardEntity.increaseViewCount();
            boardRepository.save(boardEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return IncreaseViewCountResponseDto.success();
    }

    @Override
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email) {
       try {
            boolean existedUser = userRespository.existsByEmail(email);
            if(!existedUser) return DeleteBoardResponseDto.noExistUser();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if(boardEntity == null) return DeleteBoardResponseDto.noExistBoard();

            String writerEmail = boardEntity.getWriterEmail();
            boolean isWriter = writerEmail.equals(email);
            if(!isWriter) return DeleteBoardResponseDto.noPermission();

            imageRepository.deleteByBoardNumber(boardNumber);
            commentRepository.deleteByBoardNumber(boardNumber);
            favoriteRepository.deleteByBoardNumber(boardNumber);

            boardRepository.delete(boardEntity);
       } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
       }
       return DeleteBoardResponseDto.success();
    }

    // ✨ 댓글 삭제 로직 구현
    @Override
    @Transactional // 데이터 변경 작업이므로 트랜잭션 처리 권장
    public ResponseEntity<? super DeleteCommentResponseDto> deleteComment(Integer commentNumber, String email) {
        try {
            // 1. 댓글 작성자 확인을 위해 사용자 존재 여부 확인 (선택적이지만, email 유효성 검사)
            boolean existedUser = userRespository.existsByEmail(email);
            if (!existedUser) return DeleteCommentResponseDto.noExistUser(); // 또는 적절한 오류 응답

            // 2. 삭제할 댓글 조회
            CommentEntity commentEntity = commentRepository.findById(commentNumber).orElse(null);
            if (commentEntity == null) return DeleteCommentResponseDto.noExistComment(); // 또는 적절한 오류 응답

            // 3. 댓글 작성자와 요청한 사용자가 동일한지 확인 (권한 확인)
            String commentWriterEmail = commentEntity.getUserEmail();
            boolean isWriter = email.equals(commentWriterEmail);
            if (!isWriter) return DeleteCommentResponseDto.noPermission(); // 또는 적절한 오류 응답

            // 4. 댓글 삭제
            commentRepository.delete(commentEntity);

            // 5. 해당 게시물의 댓글 수 감소
            BoardEntity boardEntity = boardRepository.findByBoardNumber(commentEntity.getBoardNumber());
            // 게시물이 존재하고, 댓글 수가 0 이상일 때만 감소 (방어 코드)
            if (boardEntity != null && boardEntity.getCommentCount() > 0) {
                boardEntity.decreaseCommentCount(); // BoardEntity에 추가한 메소드 사용
                boardRepository.save(boardEntity);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError(); // 공통 데이터베이스 오류 사용
        }

        return DeleteCommentResponseDto.success(); // 성공 응답
    }
}


