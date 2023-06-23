package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class BoardListDto {

    /**
     * 掲示情報ID
     */
    private Integer boardId;

    /**
     * 掲示情報タイトル
     */
    private String boardTitle;

    /**
     * 掲示内容
     */
    private String boardContent;
    
    /**
     * 掲示情報作成者
     */
    private String registerUserId;

    /**
     * 掲示情報作成日時
     */
    private String registerDate;

    /**
     * 掲示情報作成日時
     */
    private Integer viewCount;


}
