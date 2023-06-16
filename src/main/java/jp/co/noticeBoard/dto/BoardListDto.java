package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class BoardListDto {

    /**
     * `掲示文番号
     */
    private Integer boardId;

    /**
     * 掲示文タイトル
     */
    private String boardTitle;

    /**
     * 掲示文内容
     */
    private String boardContent;
    
    /**
     * 掲示文作成者
     */
    private String registerUserId;

    /**
     * 掲示文作成日時
     */
    private String registerDate;

    /**
     * 掲示文作成日時
     */
    private Integer viewCount;


}
