package com.example.board_back.repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board_back.entity.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    List<ImageEntity> findByBoardNumber(Integer boardNumber);
    // ✨ 지정된 boardNumber에 해당하는 이미지 개수를 반환하는 메소드 추가
    long countByBoardNumber(Integer boardNumber);
    @Transactional
    void deleteByBoardNumber(Integer boardNumber);
}
