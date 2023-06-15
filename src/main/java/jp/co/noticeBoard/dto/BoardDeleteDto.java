package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class BoardDeleteDto {

    /**
     *掲示文番号
     */
	private String boardId;

    /**
     *コメント作成者
     */
	private String RegisterUserId;

}
