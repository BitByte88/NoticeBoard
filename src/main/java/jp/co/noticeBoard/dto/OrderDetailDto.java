package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class OrderDetailDto {

    /**
     *掲示文番号
     */
	private String boardId;

    /**
     *掲示文タイトル
     */
	private String boardTitle;

    /**
     *掲示文内容
     */
	private String boardContent;

    /**
     *掲示文作成者
     */
	private String registerUserId;

    /**
     *掲示文作成日時
     */
	private String registerDate;

    /**
     *掲示文更新日時
     */
	private String updateDate;

    /**
     *掲示文更新者
     */
	private String updateUserId;

	/**
     *掲示文閲覧数
     */
	private Integer viewCount;

}
