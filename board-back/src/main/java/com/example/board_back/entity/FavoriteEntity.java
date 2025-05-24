package com.example.board_back.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.example.board_back.entity.primaryKey.FavoritePK;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="favorite")
@Table(name="favorite")
@IdClass(FavoritePK.class)
public class FavoriteEntity {

    @Id 
    private String userEmail;
    @Id
    private int boardNumber;
}
