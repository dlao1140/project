package com.example.board_back.dto.object;

import java.util.ArrayList;
import java.util.List;

import com.example.board_back.repository.resultSet.GetFavoriteListResultSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteListItme {
    private String email;
    private String nickname;
    private String profileImage;

    public FavoriteListItme(GetFavoriteListResultSet resultSet) {
        this.email = resultSet.getEmail();
        this.nickname = resultSet.getNickname();
        this.profileImage = resultSet.getProfileImage();
    }

    public static List<FavoriteListItme> copyList(List<GetFavoriteListResultSet> resultSets) {
        List<FavoriteListItme> list = new ArrayList<>();
        for(GetFavoriteListResultSet resultSet: resultSets) {
            FavoriteListItme favoriteListItem = new FavoriteListItme(resultSet);
            list.add(favoriteListItem);
        }
        return list;
    } 
}
