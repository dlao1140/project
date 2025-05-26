package com.example.board_back.dto.object;

import java.util.ArrayList;
import java.util.List;

import com.example.board_back.repository.resultSet.GetCommentListResultSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
// @AllArgsConstructor // 필드가 추가되었으므로, 모든 필드를 포함하는 생성자가 필요하면 유지하고, 아래처럼 특정 생성자를 사용하면 제거하거나 주석처리합니다.
public class CommentListItem {
    private int commentNumber;      // ✨ 필드 추가
    private String nickname;
    private String profileImage;
    private String writeDatetime;
    private String content;
    private String userEmail;       // ✨ 필드 추가
    
    public CommentListItem(GetCommentListResultSet resultSet) {
        this.commentNumber = resultSet.getCommentNumber();    // ✨ 매핑 추가
        this.nickname = resultSet.getNickname();
        this.profileImage = resultSet.getProfileImage();
        this.writeDatetime = resultSet.getWriteDatetime();
        this.content = resultSet.getContent();
        this.userEmail = resultSet.getUserEmail();        // ✨ 매핑 추가
    }

    public static List<CommentListItem> copyList(List<GetCommentListResultSet> resultSets) {
        List<CommentListItem> list = new ArrayList<>();
        for (GetCommentListResultSet resultSet : resultSets) {
            CommentListItem commentListItem = new CommentListItem(resultSet);
            list.add(commentListItem);
        }
        return list;
    }
}