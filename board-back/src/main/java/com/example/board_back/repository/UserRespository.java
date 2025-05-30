package com.example.board_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board_back.entity.UserEntity;

@Repository
public interface UserRespository extends JpaRepository<UserEntity, String> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByTelNumber(String telNumber);

    UserEntity findByEmail(String email);
    
}
