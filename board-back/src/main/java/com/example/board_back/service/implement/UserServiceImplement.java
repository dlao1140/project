package com.example.board_back.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.board_back.dto.request.user.PatchNicknameRequestDto;
import com.example.board_back.dto.request.user.PatchProfileImageRequestDto;
import com.example.board_back.dto.response.ResponseDto;
import com.example.board_back.dto.response.user.GetSignInUserResponseDto;
import com.example.board_back.dto.response.user.GetUserResponseDto;
import com.example.board_back.dto.response.user.PatchNicknameResponseDto;
import com.example.board_back.dto.response.user.PatchProfileImageResponseDto;
import com.example.board_back.entity.UserEntity;
import com.example.board_back.repository.UserRespository;
import com.example.board_back.service.UserService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService{

    private final UserRespository userRespository;

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String email) {

        UserEntity userEntity = null;
        try {
            
            userEntity = userRespository.findByEmail(email);
            if(userEntity == null) return GetUserResponseDto.noExistUser();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }   
        return GetUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {

        UserEntity userEntity = null;

        try {

            userEntity = userRespository.findByEmail(email);
            if(userEntity == null) return GetSignInUserResponseDto.notExistUser();

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email) {
        try {

            UserEntity userEntity = userRespository.findByEmail(email);
            if(userEntity == null) PatchNicknameResponseDto.noExistUser();

            String nickname = dto.getNickname();
            boolean existedNickname = userRespository.existsByNickname(nickname);
            if(existedNickname) return PatchNicknameResponseDto.duplicateNickname();

            userEntity.setNickname(nickname);
            userRespository.save(userEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchNicknameResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email) {
        try {

            UserEntity userEntity = userRespository.findByEmail(email);
            if(userEntity == null) return PatchProfileImageResponseDto.noExistUser();

            String profileImage = dto.getProfileImage();
            userEntity.setProfileImage(profileImage);
            userRespository.save(userEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchProfileImageResponseDto.success();
    }

    
}
