package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class BoardCommentDto {

    /**
     *掲示文番号
     */
	private String boardId;

    /**
     *コメント内容
     */
	private String commentContent;

    /**
     *コメント作成者
     */
	private String commentRegisterUserId;

    /**
     *コメント作成日時
     */
	private String commentRegisterDate;

}
