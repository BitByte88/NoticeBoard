package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class BoardDetailDto {

    /**
     *掲示No
     */
	private String boardId;

    /**
     *掲示情報タイトル
     */
	private String boardTitle;

    /**
     *掲示情報内容
     */
	private String boardContent;

    /**
     *掲示情報作成者
     */
	private String registerUserId;

    /**
     *掲示情報作成日時
     */
	private String registerDate;

    /**
     *掲示情報更新日時
     */
	private String updateDate;

    /**
     *掲示情報更新者
     */
	private String updateUserId;

	/**
     *掲示情報閲覧数
     */
	private Integer viewCount;

}
